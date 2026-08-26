package com.example.data.repository

import com.example.data.model.CourseModule
import com.example.data.model.Lesson
import com.example.data.model.LessonSections
import com.example.data.model.QuizQuestion
import com.example.data.model.TermItem
import com.example.data.model.TrendDiagnosticItem

object CourseDataProvider {

    val allModules: List<CourseModule> by lazy {
        listOf(
            createModule0(),
            createModule1(),
            createModule2(),
            createModule3(),
            createModule4(),
            createModule5(),
            createModule6(),
            createModule7(),
            createModule8(),
            createModule9(),
            createModule10(),
            createModule11(),
            createModule12(),
            createCasesModule(),
            createExercisesModule(),
            createDiscussionModule(),
            createAnswerKeyModule()
        )
    }

    private fun createExercisesModule(): CourseModule {
        return getExercisesData()
    }

    private fun createCasesModule(): CourseModule {
        return getCasesData()
    }

    private fun createDiscussionModule(): CourseModule {
        return getDiscussionData()
    }

    private fun createModule0(): CourseModule {
        return getModule0Data()
    }

    private fun createModule1(): CourseModule {
        return getModule1Data()
    }

    private fun createModule2(): CourseModule {
        return getModule2Data()
    }

    private fun createModule3(): CourseModule {
        return getModule3Data()
    }

    private fun createModule4(): CourseModule {
        return getModule4Data()
    }

    private fun createModule5(): CourseModule {
        return getModule5Data()
    }

    private fun createModule6(): CourseModule {
        return getModule6Data()
    }

    private fun createModule7(): CourseModule {
        return getModule7Data()
    }

    private fun createModule8(): CourseModule {
        return getModule8Data()
    }

    private fun createModule9(): CourseModule {
        return getModule9Data()
    }

    private fun createModule10(): CourseModule {
        return getModule10Data()
    }

    private fun createModule11(): CourseModule {
        return getModule11Data()
    }

    private fun createModule12(): CourseModule {
        return getModule12Data()
    }

    private fun createAnswerKeyModule(): CourseModule {
        return getCompleteAnswerKeyModule()
    }

    private fun createPlaceholderModule(
        id: String,
        number: String,
        title: String,
        subtitle: String,
        orderIndex: Int
    ): CourseModule {
        val defaultLessonNumber = "${orderIndex}.1"
        return CourseModule(
            id = id,
            number = number,
            title = title,
            subtitle = subtitle,
            orderIndex = orderIndex,
            lessons = listOf(
                Lesson(
                    id = "lesson_${id}_1",
                    moduleId = id,
                    number = defaultLessonNumber,
                    title = "$title — Суурь үндэс ба тойм",
                    summary = "$subtitle тухай цогц онол ба инженерийн тооцооны загвар",
                    estimatedMinutes = 15,
                    sections = LessonSections(
                        зорилго = "1. $title сэдвийн гол концепц болон үйл ажиллагааны үндсийг ойлгох.\n2. Үйлдвэрийн талбарт хэрэглэгдэх инженерийн суурь хуулиудыг хэрэгжүүлэх.",
                        нэр_томьёо = listOf(
                            TermItem("Суурь параметр ($title)", "Тухайн процессын үр ашгийг тодорхойлогч үндсэн физик хэмжигдэхүүн.", id, defaultLessonNumber),
                            TermItem("Стандарт үйл ажиллагааны горим (SOP)", "Аюулгүй, үр дүнтэй ажиллагааг хангах батлагдсан зааварчилгаа.", id, defaultLessonNumber)
                        ),
                        онол = "$title-ийн онолын үндэс нь масс, энерги ба импульсийн хадгалагдах суурь хуулиудад тулгуурладаг. Цаашид номын дараагийн хэсгүүдээр энэ бүлгийн хичээлүүд нэмэгдэнэ.",
                        механизм = "Процессын параметрүүдийн харилцан хамаарал ба шалтгаан-үр дагаврын динамик загварчлал.",
                        хувьсагч_нэгж = "SI нэгжийн системийн дагуу үндсэн ба уламжлагдсан хэмжигдэхүүнүүдийг ашиглана.",
                        гарган_авалт = "Тогтворжсон төлөвийн үндсэн дифференциал тэгшитгэлүүдийн интегралчлал.",
                        жишээ = "$title сэдвийн хүрээнд үйлдвэрийн тооцооны жишээ бодлого энд байрлана.",
                        нотолгоо = "Үйлдвэрийн талбайн бодит туршилт болон DCS хэмжилтийн өгөгдлөөр баталгаажсан.",
                        тренд_оношлол = listOf(
                            TrendDiagnosticItem(
                                signal = "Процессын хэвийн бус хэлбэлзэл",
                                evidence = "Мэдрэгч дээр өгөгдөл заасан хязгаараас хэтрэх",
                                wrongConclusion = "Зөвхөн нэг параметрийг тохируулах",
                                correctAction = "Системийн нийт баланс ба холбогдох хаалтуудыг цогцоор нь шалгах"
                            )
                        ),
                        алдаа = "Аюулгүй ажиллагааны зааврыг зөрчиж процессын хязгаарыг дур мэдэн өөрчлөх.",
                        дүгнэлт = "$title-ийн гол дүгнэлт ба цаашид анхаарах инженерийн зөвлөмжүүд.",
                        шалгах_асуултууд = listOf(
                            QuizQuestion(
                                id = "q_${id}_1",
                                question = "$title сэдвийн хүрээнд үйлдвэрийн горимыг тодорхойлоход хамгийн чухал хүчин зүйл юу вэ?",
                                options = listOf(
                                    "Масс ба энергийн баланс, аюулгүй ажиллагааны горим",
                                    "Зөвхөн тоног төхөөрөмжийн өнгө",
                                    "Цаг агаарын нөлөөг үл тооцох",
                                    "Ажилчдын тоо"
                                ),
                                correctAnswer = "Масс ба энергийн баланс, аюулгүй ажиллагааны горим",
                                explanation = "Химийн инженерийн бүх үйл ажиллагаа масс-энергийн баланс ба аюулгүй байдлын дүрэмд суурилдаг."
                            )
                        ),
                        эх_сурвалж = "Chemical Engineering Master Field Guide 2026."
                    )
                )
            )
        )
    }

    fun getAllTerms(): List<TermItem> {
        val list = mutableListOf<TermItem>()
        allModules.forEach { mod ->
            mod.lessons.forEach { lesson ->
                list.addAll(lesson.sections.нэр_томьёо)
            }
        }
        return list.sortedBy { it.name }
    }

    fun findLesson(lessonId: String): Lesson? {
        for (module in allModules) {
            for (lesson in module.lessons) {
                if (lesson.id == lessonId) return lesson
            }
        }
        return null
    }

    fun findModule(moduleId: String): CourseModule? {
        return allModules.find { it.id == moduleId }
    }
}
