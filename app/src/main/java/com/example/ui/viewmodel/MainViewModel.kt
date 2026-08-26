package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.LessonProgressEntity
import com.example.data.local.QuizAttemptEntity
import com.example.data.model.CourseModule
import com.example.data.model.Lesson
import com.example.data.model.TermItem
import com.example.data.repository.CourseRepository
import com.example.data.repository.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FontSizeScale(val scale: Float, val label: String) {
    NORMAL(1.0f, "Хэвийн"),
    LARGE(1.15f, "Том"),
    EXTRA_LARGE(1.35f, "Маш том")
}

data class AppUiState(
    val isDarkMode: Boolean = false,
    val fontSizeScale: FontSizeScale = FontSizeScale.NORMAL,
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val selectedCategoryTermLetter: String? = null,
    val termSearchQuery: String = "",
    val activeLessonSectionIndex: Int = 0
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "chemical_guide.db"
    ).fallbackToDestructiveMigration().build()

    val repository = CourseRepository(db.appDao())

    val modules: List<CourseModule> = repository.getModules()
    val allTerms: List<TermItem> = repository.getAllTerms()

    val progressMap: StateFlow<Map<String, LessonProgressEntity>> =
        repository.observeAllProgress().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    val quizAttempts: StateFlow<List<QuizAttemptEntity>> =
        repository.observeQuizAttempts().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(isDarkMode = !_uiState.value.isDarkMode)
    }

    fun setFontSizeScale(scale: FontSizeScale) {
        _uiState.value = _uiState.value.copy(fontSizeScale = scale)
    }

    fun onSearchQueryChange(query: String) {
        val results = if (query.isBlank()) emptyList() else repository.search(query)
        _uiState.value = _uiState.value.copy(searchQuery = query, searchResults = results)
    }

    fun onTermSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(termSearchQuery = query)
    }

    fun onTermLetterFilter(letter: String?) {
        _uiState.value = _uiState.value.copy(selectedCategoryTermLetter = letter)
    }

    fun toggleLessonCompleted(lessonId: String, moduleId: String) {
        viewModelScope.launch {
            val isCompleted = progressMap.value[lessonId]?.isCompleted ?: false
            repository.toggleLessonCompleted(lessonId, moduleId, isCompleted)
        }
    }

    fun toggleBookmark(lessonId: String, moduleId: String) {
        viewModelScope.launch {
            val isBookmarked = progressMap.value[lessonId]?.isBookmarked ?: false
            repository.toggleBookmark(lessonId, moduleId, isBookmarked)
        }
    }

    fun markLessonRead(lessonId: String, moduleId: String) {
        viewModelScope.launch {
            repository.markLessonRead(lessonId, moduleId)
        }
    }

    fun recordQuizResult(quizId: String, lessonId: String, score: Int, total: Int) {
        viewModelScope.launch {
            repository.recordQuizAttempt(quizId, lessonId, score, total)
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
        }
    }

    fun getLesson(lessonId: String): Lesson? = repository.getLesson(lessonId)

    fun getModule(moduleId: String): CourseModule? = repository.getModule(moduleId)
}
