package com.example.data.repository

import com.example.data.model.CourseModule

fun getCompleteAnswerKeyModule(): CourseModule {
    return CourseModule(
        id = "mod_key",
        number = "НЭМЭЛТ Г",
        title = "Хариултын нэгдсэн сан",
        subtitle = "Бүх бүлэг, сэдвүүдийн (0.1—0.7, 1—91) асуултуудын инженерийн баталгаат хариултууд",
        orderIndex = 16,
        lessons = listOf(
            getAnswerKeyModulePart1(),
            getAnswerKeyModulePart2(),
            getAnswerKeyModulePart3(),
            getAnswerKeyModulePart4(),
            getAnswerKeyModulePart5()
        )
    )
}
