package com.example.data.model

data class CourseModule(
    val id: String,
    val number: String,
    val title: String,
    val subtitle: String,
    val orderIndex: Int,
    val lessons: List<Lesson> = emptyList()
)

data class Lesson(
    val id: String,
    val moduleId: String,
    val number: String,
    val title: String,
    val summary: String = "",
    val estimatedMinutes: Int = 15,
    val sections: LessonSections
)

data class LessonSections(
    val зорилго: String,
    val нэр_томьёо: List<TermItem> = emptyList(),
    val онол: String,
    val механизм: String,
    val хувьсагч_нэгж: String,
    val гарган_авалт: String,
    val жишээ: String,
    val нотолгоо: String,
    val тренд_оношлол: List<TrendDiagnosticItem> = emptyList(),
    val алдаа: String,
    val дүгнэлт: String,
    val шалгах_асуултууд: List<QuizQuestion> = emptyList(),
    val эх_сурвалж: String
)

data class TermItem(
    val name: String,
    val definition: String,
    val moduleId: String = "",
    val lessonNumber: String = ""
)

data class TrendDiagnosticItem(
    val signal: String,
    val evidence: String,
    val wrongConclusion: String,
    val correctAction: String = ""
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String> = emptyList(),
    val correctAnswer: String,
    val explanation: String
)

enum class SectionType(val title: String, val icon: String) {
    PURPOSE("1. Суралцах зорилго", "flag"),
    TERMS("2. Нэр томьёо", "menu_book"),
    THEORY("3. Суурь онол", "science"),
    MECHANISM("4. Механизм — шалтгаан ба үр дагавар", "alt_route"),
    VARIABLES("5. Хувьсагч, нэгж ба физикийн давхарга", "square_foot"),
    DERIVATION("6. Гарган авалт ба таамаглал", "functions"),
    EXAMPLES("7. Бодсон жишээ", "calculate"),
    FIELD_PROOF("8. Ажилтай холбох нотолгоо", "fact_check"),
    DIAGNOSTICS("9. Тренд ба оношлол", "analytics"),
    ERRORS("10. Түгээмэл алдаа", "warning"),
    CONCLUSION("11. Гол дүгнэлт", "task_alt"),
    QUIZ("12. Өөрийгөө шалгах", "quiz"),
    REFERENCES("13. Эх сурвалж ба баталгаажуулалт", "verified")
}
