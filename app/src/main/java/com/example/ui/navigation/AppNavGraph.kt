package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.ui.screens.GlossaryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.ModuleDetailScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.viewmodel.MainViewModel

sealed class AppScreen {
    object Home : AppScreen()
    data class ModuleDetail(val moduleId: String) : AppScreen()
    data class LessonDetail(val lessonId: String) : AppScreen()
    data class Quiz(val lessonId: String) : AppScreen()
    object Glossary : AppScreen()
    object Search : AppScreen()
    object Progress : AppScreen()
    object Settings : AppScreen()
}

@Composable
fun AppNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<AppScreen>(AppScreen.Home) }
    val currentScreen = backStack.lastOrNull() ?: AppScreen.Home

    val navigateTo: (AppScreen) -> Unit = { screen ->
        backStack.add(screen)
    }

    val popBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    BackHandler(enabled = backStack.size > 1) {
        popBack()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val screen = currentScreen) {
            is AppScreen.Home -> {
                HomeScreen(
                    viewModel = viewModel,
                    onModuleClick = { moduleId -> navigateTo(AppScreen.ModuleDetail(moduleId)) },
                    onLessonClick = { lessonId -> navigateTo(AppScreen.LessonDetail(lessonId)) },
                    onNavigateToSearch = { navigateTo(AppScreen.Search) },
                    onNavigateToGlossary = { navigateTo(AppScreen.Glossary) },
                    onNavigateToProgress = { navigateTo(AppScreen.Progress) },
                    onNavigateToSettings = { navigateTo(AppScreen.Settings) }
                )
            }
            is AppScreen.ModuleDetail -> {
                ModuleDetailScreen(
                    moduleId = screen.moduleId,
                    viewModel = viewModel,
                    onBackClick = popBack,
                    onLessonClick = { lessonId -> navigateTo(AppScreen.LessonDetail(lessonId)) },
                    onQuizClick = { lessonId -> navigateTo(AppScreen.Quiz(lessonId)) }
                )
            }
            is AppScreen.LessonDetail -> {
                LessonDetailScreen(
                    lessonId = screen.lessonId,
                    viewModel = viewModel,
                    onBackClick = popBack,
                    onQuizClick = { lessonId -> navigateTo(AppScreen.Quiz(lessonId)) }
                )
            }
            is AppScreen.Quiz -> {
                QuizScreen(
                    lessonId = screen.lessonId,
                    viewModel = viewModel,
                    onBackClick = popBack
                )
            }
            is AppScreen.Glossary -> {
                GlossaryScreen(
                    viewModel = viewModel,
                    onBackClick = popBack,
                    onLessonClick = { lessonId -> navigateTo(AppScreen.LessonDetail(lessonId)) }
                )
            }
            is AppScreen.Search -> {
                SearchScreen(
                    viewModel = viewModel,
                    onBackClick = popBack,
                    onLessonClick = { lessonId -> navigateTo(AppScreen.LessonDetail(lessonId)) }
                )
            }
            is AppScreen.Progress -> {
                ProgressScreen(
                    viewModel = viewModel,
                    onBackClick = popBack,
                    onLessonClick = { lessonId -> navigateTo(AppScreen.LessonDetail(lessonId)) }
                )
            }
            is AppScreen.Settings -> {
                SettingsScreen(
                    viewModel = viewModel,
                    onBackClick = popBack
                )
            }
        }
    }
}
