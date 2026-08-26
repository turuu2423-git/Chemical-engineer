package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppHeader
import com.example.ui.components.ExpandableSectionCard
import com.example.ui.components.SafetyDisclaimerBanner
import com.example.ui.components.TerminologyTableView
import com.example.ui.components.TrendDiagnosticsTableView
import com.example.ui.theme.Amber500
import com.example.ui.theme.Amber600
import com.example.ui.theme.DangerRed
import com.example.ui.theme.Mint600
import com.example.ui.theme.Navy800
import com.example.ui.theme.SafetyOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.FontSizeScale
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    lessonId: String,
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
    onQuizClick: (String) -> Unit
) {
    val lesson = viewModel.getLesson(lessonId)
    val progressMap by viewModel.progressMap.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var showFontSizeMenu by remember { mutableStateOf(false) }

    LaunchedEffect(lessonId) {
        if (lesson != null) {
            viewModel.markLessonRead(lessonId, lesson.moduleId)
        }
    }

    if (lesson == null) {
        Scaffold(
            topBar = { AppHeader(title = "Хичээл олдсонгүй", onBackClick = onBackClick) }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Хичээл олдсонгүй.")
            }
        }
        return
    }

    val isCompleted = progressMap[lesson.id]?.isCompleted == true
    val isBookmarked = progressMap[lesson.id]?.isBookmarked == true

    // Scaled text style factor
    val textScale = uiState.fontSizeScale.scale
    val bodyFontSize = (15 * textScale).sp
    val bodyLineHeight = (23 * textScale).sp

    Scaffold(
        topBar = {
            AppHeader(
                title = "Хичээл ${lesson.number}",
                subtitle = lesson.title,
                onBackClick = onBackClick,
                actions = {
                    Box {
                        IconButton(
                            onClick = { showFontSizeMenu = true },
                            modifier = Modifier.testTag("font_size_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "Фонтын хэмжээ",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        DropdownMenu(
                            expanded = showFontSizeMenu,
                            onDismissRequest = { showFontSizeMenu = false }
                        ) {
                            FontSizeScale.values().forEach { scale ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = scale.label,
                                            fontWeight = if (uiState.fontSizeScale == scale) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        viewModel.setFontSizeScale(scale)
                                        showFontSizeMenu = false
                                    }
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { viewModel.toggleBookmark(lesson.id, lesson.moduleId) },
                        modifier = Modifier.testTag("lesson_detail_bookmark")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Хадгалах",
                            tint = if (isBookmarked) Amber500 else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.toggleLessonCompleted(lesson.id, lesson.moduleId) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("lesson_detail_complete_button"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) SuccessGreen else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCompleted) "Үзсэн гэж тэмдэглэсэн" else "Үзсэн гэж тэмдэглэх",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    if (lesson.sections.шалгах_асуултууд.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { onQuizClick(lesson.id) },
                            modifier = Modifier.testTag("lesson_detail_quiz_button"),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Amber600
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Тест өгөх", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Quick Jump Horizontal Section Index
            val sectionsNames = listOf(
                "1. Зорилго",
                "2. Нэр томьёо",
                "3. Онол",
                "4. Механизм",
                "5. Хувьсагч",
                "6. Гарган авалт",
                "7. Жишээ",
                "8. Нотолгоо",
                "9. Тренд",
                "10. Алдаа",
                "11. Дүгнэлт",
                "12. Тест",
                "13. Эх сурвалж"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                sectionsNames.forEachIndexed { index, name ->
                    FilterChip(
                        selected = false,
                        onClick = {
                            scope.launch {
                                // Jump to item in LazyColumn (offset 2 for header items)
                                listState.animateScrollToItem((index + 2).coerceAtMost(14))
                            }
                        },
                        label = { Text(name, style = MaterialTheme.typography.labelMedium) },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Lesson Title Banner
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy800)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "ХИЧЭЭЛ ${lesson.number}",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = Amber500,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Унших хугацаа: ${lesson.estimatedMinutes} мин",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                // Safety Banner
                item {
                    SafetyDisclaimerBanner()
                }

                // 1. Суралцах зорилго
                item {
                    ExpandableSectionCard(
                        sectionNumber = 1,
                        title = "1. Суралцах зорилго",
                        icon = Icons.Default.Flag
                    ) {
                        Text(
                            text = lesson.sections.зорилго,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyFontSize,
                                lineHeight = bodyLineHeight,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // 2. Нэр томьёо (Хүснэгт)
                item {
                    ExpandableSectionCard(
                        sectionNumber = 2,
                        title = "2. Нэр томьёо ба Тодорхойлолт",
                        icon = Icons.Default.MenuBook
                    ) {
                        TerminologyTableView(terms = lesson.sections.нэр_томьёо)
                    }
                }

                // 3. Суурь онол
                item {
                    ExpandableSectionCard(
                        sectionNumber = 3,
                        title = "3. Суурь онол",
                        icon = Icons.Default.Science
                    ) {
                        Text(
                            text = lesson.sections.онол,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyFontSize,
                                lineHeight = bodyLineHeight,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // 4. Механизм — шалтгаан ба үр дагавар
                item {
                    ExpandableSectionCard(
                        sectionNumber = 4,
                        title = "4. Механизм — Шалтгаан ба үр дагавар",
                        icon = Icons.Default.AltRoute
                    ) {
                        Text(
                            text = lesson.sections.механизм,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyFontSize,
                                lineHeight = bodyLineHeight,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // 5. Хувьсагч, нэгж ба физикийн давхарга
                item {
                    ExpandableSectionCard(
                        sectionNumber = 5,
                        title = "5. Хувьсагч, нэгж ба физикийн давхарга",
                        icon = Icons.Default.SquareFoot
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = lesson.sections.хувьсагч_нэгж,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = bodyFontSize,
                                    lineHeight = bodyLineHeight,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }

                // 6. Гарган авалт ба таамаглал
                item {
                    ExpandableSectionCard(
                        sectionNumber = 6,
                        title = "6. Гарган авалт ба таамаглал",
                        icon = Icons.Default.Functions
                    ) {
                        Text(
                            text = lesson.sections.гарган_авалт,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyFontSize,
                                lineHeight = bodyLineHeight,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // 7. Бодсон жишээ
                item {
                    ExpandableSectionCard(
                        sectionNumber = 7,
                        title = "7. Бодсон жишээ ба тооцоо",
                        icon = Icons.Default.Calculate
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Mint600.copy(alpha = 0.08f)
                            ),
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Text(
                                text = lesson.sections.жишээ,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = bodyFontSize,
                                    lineHeight = bodyLineHeight,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                // 8. Ажилтай холбох нотолгоо
                item {
                    ExpandableSectionCard(
                        sectionNumber = 8,
                        title = "8. Ажилтай холбох нотолгоо (Field Proof)",
                        icon = Icons.Default.FactCheck
                    ) {
                        Text(
                            text = lesson.sections.нотолгоо,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = bodyFontSize,
                                lineHeight = bodyLineHeight,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                // 9. Тренд ба оношлол (Хүснэгт)
                item {
                    ExpandableSectionCard(
                        sectionNumber = 9,
                        title = "9. Тренд ба оношлол (Diagnostics)",
                        icon = Icons.Default.Analytics
                    ) {
                        TrendDiagnosticsTableView(diagnostics = lesson.sections.тренд_оношлол)
                    }
                }

                // 10. Түгээмэл алдаа
                item {
                    ExpandableSectionCard(
                        sectionNumber = 10,
                        title = "10. Түгээмэл алдаа",
                        icon = Icons.Default.Warning
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = DangerRed.copy(alpha = 0.08f)
                            )
                        ) {
                            Text(
                                text = lesson.sections.алдаа,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = bodyFontSize,
                                    lineHeight = bodyLineHeight,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                // 11. Гол дүгнэлт
                item {
                    ExpandableSectionCard(
                        sectionNumber = 11,
                        title = "11. Гол дүгнэлт",
                        icon = Icons.Default.TaskAlt
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Amber500.copy(alpha = 0.1f)
                            )
                        ) {
                            Text(
                                text = lesson.sections.дүгнэлт,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = bodyFontSize,
                                    lineHeight = bodyLineHeight,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                // 12. Өөрийгөө шалгах (Тестийн асуултууд)
                item {
                    ExpandableSectionCard(
                        sectionNumber = 12,
                        title = "12. Өөрийгөө шалгах тест (${lesson.sections.шалгах_асуултууд.size})",
                        icon = Icons.Default.Quiz
                    ) {
                        Column {
                            lesson.sections.шалгах_асуултууд.forEachIndexed { index, q ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "Асуулт ${index + 1}: ${q.question}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { onQuizClick(lesson.id) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("launch_quiz_interactive_button"),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Amber600
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Интерактив тест өгөх",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                // 13. Эх сурвалж ба баталгаажуулалтын цэг
                item {
                    ExpandableSectionCard(
                        sectionNumber = 13,
                        title = "13. Эх сурвалж ба баталгаажуулалт",
                        icon = Icons.Default.Verified
                    ) {
                        Text(
                            text = lesson.sections.эх_сурвалж,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}
