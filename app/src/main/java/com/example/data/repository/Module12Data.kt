package com.example.data.repository

import com.example.data.model.CourseModule

fun getModule12Data(): CourseModule {
    val allLessons = getModule12LessonsPart1() + getModule12LessonsPart2()
    return CourseModule(
        id = "mod_12",
        number = "XII МОДУЛЬ",
        title = "HSE, хууль, онцгой байдал ба мэргэжлийн хариуцлага",
        subtitle = "GHS/SDS, химийн хадгалалт, Монголын хууль/лиценз, chain of custody, HAZID/HAZOP/LOPA, MOC/PTW, эрүүл ахуй/PPE, асгаралт, онцгой байдал, reactive ground, human factors, RCA ба ёс зүй",
        orderIndex = 12,
        lessons = allLessons
    )
}
