package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.local.LessonProgressEntity
import com.example.data.local.QuizAttemptEntity
import com.example.data.local.UserNoteEntity
import com.example.data.model.CourseModule
import com.example.data.model.Lesson
import com.example.data.model.TermItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class SearchResult(
    val moduleId: String,
    val moduleNumber: String,
    val lessonId: String,
    val lessonNumber: String,
    val lessonTitle: String,
    val matchedSection: String,
    val snippet: String
)

class CourseRepository(private val dao: AppDao) {

    fun getModules(): List<CourseModule> {
        return CourseDataProvider.allModules
    }

    fun getModule(moduleId: String): CourseModule? {
        return CourseDataProvider.findModule(moduleId)
    }

    fun getLesson(lessonId: String): Lesson? {
        return CourseDataProvider.findLesson(lessonId)
    }

    fun getAllTerms(): List<TermItem> {
        return CourseDataProvider.getAllTerms()
    }

    fun observeAllProgress(): Flow<Map<String, LessonProgressEntity>> {
        return dao.getAllProgress().map { list ->
            list.associateBy { it.lessonId }
        }
    }

    fun observeLessonProgress(lessonId: String): Flow<LessonProgressEntity?> {
        return dao.observeProgressForLesson(lessonId)
    }

    suspend fun toggleLessonCompleted(lessonId: String, moduleId: String, isCurrentlyCompleted: Boolean) {
        val existing = dao.getProgressForLesson(lessonId)
        val newStatus = !isCurrentlyCompleted
        val completedAt = if (newStatus) System.currentTimeMillis() else null
        
        if (existing != null) {
            dao.updateLessonCompletion(lessonId, newStatus, completedAt)
        } else {
            dao.insertOrUpdateProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    moduleId = moduleId,
                    isCompleted = newStatus,
                    completedAt = completedAt,
                    lastReadAt = System.currentTimeMillis(),
                    isBookmarked = false
                )
            )
        }
    }

    suspend fun toggleBookmark(lessonId: String, moduleId: String, isCurrentlyBookmarked: Boolean) {
        val existing = dao.getProgressForLesson(lessonId)
        val newBookmarkStatus = !isCurrentlyBookmarked
        
        if (existing != null) {
            dao.updateBookmark(lessonId, newBookmarkStatus)
        } else {
            dao.insertOrUpdateProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    moduleId = moduleId,
                    isCompleted = false,
                    isBookmarked = newBookmarkStatus,
                    lastReadAt = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun markLessonRead(lessonId: String, moduleId: String) {
        val existing = dao.getProgressForLesson(lessonId)
        if (existing != null) {
            dao.insertOrUpdateProgress(existing.copy(lastReadAt = System.currentTimeMillis()))
        } else {
            dao.insertOrUpdateProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    moduleId = moduleId,
                    isCompleted = false,
                    lastReadAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun observeQuizAttempts(): Flow<List<QuizAttemptEntity>> {
        return dao.getAllQuizAttempts()
    }

    suspend fun recordQuizAttempt(quizId: String, lessonId: String, score: Int, totalQuestions: Int) {
        dao.insertQuizAttempt(
            QuizAttemptEntity(
                quizId = quizId,
                lessonId = lessonId,
                score = score,
                totalQuestions = totalQuestions,
                attemptedAt = System.currentTimeMillis()
            )
        )
    }

    fun observeUserNote(lessonId: String): Flow<UserNoteEntity?> {
        return dao.getNoteForLesson(lessonId)
    }

    suspend fun saveUserNote(lessonId: String, noteText: String) {
        dao.saveNote(UserNoteEntity(lessonId = lessonId, noteText = noteText, updatedAt = System.currentTimeMillis()))
    }

    suspend fun resetAllProgress() {
        dao.clearAllProgress()
        dao.clearAllQuizAttempts()
    }

    fun search(query: String): List<SearchResult> {
        val clean = query.trim().lowercase()
        if (clean.isBlank() || clean.length < 2) return emptyList()

        val results = mutableListOf<SearchResult>()

        for (mod in CourseDataProvider.allModules) {
            for (lesson in mod.lessons) {
                // Check title
                if (lesson.title.lowercase().contains(clean) || lesson.summary.lowercase().contains(clean)) {
                    results.add(
                        SearchResult(
                            moduleId = mod.id,
                            moduleNumber = mod.number,
                            lessonId = lesson.id,
                            lessonNumber = lesson.number,
                            lessonTitle = lesson.title,
                            matchedSection = "Гарчиг ба тойм",
                            snippet = lesson.summary.ifBlank { lesson.title }
                        )
                    )
                }

                // Check Terms
                for (term in lesson.sections.нэр_томьёо) {
                    if (term.name.lowercase().contains(clean) || term.definition.lowercase().contains(clean)) {
                        results.add(
                            SearchResult(
                                moduleId = mod.id,
                                moduleNumber = mod.number,
                                lessonId = lesson.id,
                                lessonNumber = lesson.number,
                                lessonTitle = lesson.title,
                                matchedSection = "Нэр томьёо: ${term.name}",
                                snippet = term.definition
                            )
                        )
                    }
                }

                // Check Theory
                if (lesson.sections.онол.lowercase().contains(clean)) {
                    results.add(
                        SearchResult(
                            moduleId = mod.id,
                            moduleNumber = mod.number,
                            lessonId = lesson.id,
                            lessonNumber = lesson.number,
                            lessonTitle = lesson.title,
                            matchedSection = "Суурь онол",
                            snippet = extractSnippet(lesson.sections.онол, clean)
                        )
                    )
                }

                // Check Mechanism
                if (lesson.sections.механизм.lowercase().contains(clean)) {
                    results.add(
                        SearchResult(
                            moduleId = mod.id,
                            moduleNumber = mod.number,
                            lessonId = lesson.id,
                            lessonNumber = lesson.number,
                            lessonTitle = lesson.title,
                            matchedSection = "Механизм",
                            snippet = extractSnippet(lesson.sections.механизм, clean)
                        )
                    )
                }

                // Check Worked Example
                if (lesson.sections.жишээ.lowercase().contains(clean)) {
                    results.add(
                        SearchResult(
                            moduleId = mod.id,
                            moduleNumber = mod.number,
                            lessonId = lesson.id,
                            lessonNumber = lesson.number,
                            lessonTitle = lesson.title,
                            matchedSection = "Бодсон жишээ",
                            snippet = extractSnippet(lesson.sections.жишээ, clean)
                        )
                    )
                }

                // Check Diagnostics
                for (diag in lesson.sections.тренд_оношлол) {
                    if (diag.signal.lowercase().contains(clean) || diag.evidence.lowercase().contains(clean) || diag.correctAction.lowercase().contains(clean)) {
                        results.add(
                            SearchResult(
                                moduleId = mod.id,
                                moduleNumber = mod.number,
                                lessonId = lesson.id,
                                lessonNumber = lesson.number,
                                lessonTitle = lesson.title,
                                matchedSection = "Тренд ба оношлол",
                                snippet = "${diag.signal} -> ${diag.correctAction}"
                            )
                        )
                    }
                }
            }
        }

        return results.take(40)
    }

    private fun extractSnippet(text: String, query: String): String {
        val idx = text.lowercase().indexOf(query)
        if (idx == -1) return text.take(120)
        val start = (idx - 40).coerceAtLeast(0)
        val end = (idx + query.length + 80).coerceAtMost(text.length)
        val prefix = if (start > 0) "..." else ""
        val suffix = if (end < text.length) "..." else ""
        return prefix + text.substring(start, end).replace("\n", " ") + suffix
    }
}
