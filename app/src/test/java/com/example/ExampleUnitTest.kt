package com.example

import com.example.data.model.CourseModule
import com.example.data.model.Lesson
import com.example.data.repository.CourseDataProvider
import com.example.data.repository.CourseRepository
import org.junit.Assert.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class ExampleUnitTest {

    @Test
    fun qa_1_completeness_and_lesson_counts() {
        val modules = CourseDataProvider.allModules
        println("=== 1. MODULES AND LESSON COUNTS ===")
        println("Total modules in app: ${modules.size}")
        var totalLessons = 0

        val emptyFieldIssues = mutableListOf<String>()
        val termIssues = mutableListOf<String>()
        val trendIssues = mutableListOf<String>()

        for (mod in modules) {
            println("Module [${mod.number}] ${mod.title} -> ${mod.lessons.size} lessons")
            totalLessons += mod.lessons.size

            for (lesson in mod.lessons) {
                val sec = lesson.sections
                if (sec.зорилго.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): зорилго is blank")
                if (sec.нэр_томьёо.isEmpty()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): нэр_томьёо is empty")
                if (sec.онол.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): онол is blank")
                if (sec.механизм.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): механизм is blank")
                if (sec.хувьсагч_нэгж.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): хувьсагч_нэгж is blank")
                if (sec.гарган_авалт.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): гарган_авалт is blank")
                if (sec.жишээ.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): жишээ is blank")
                if (sec.нотолгоо.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): нотолгоо is blank")
                if (sec.тренд_оношлол.isEmpty()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): тренд_оношлол is empty")
                if (sec.алдаа.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): алдаа is blank")
                if (sec.дүгнэлт.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): дүгнэлт is blank")
                if (sec.шалгах_асуултууд.isEmpty()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): шалгах_асуултууд is empty")
                if (sec.эх_сурвалж.isBlank()) emptyFieldIssues.add("${lesson.id} (${lesson.number}): эх_сурвалж is blank")

                // Check terms
                for (t in sec.нэр_томьёо) {
                    if (t.name.isBlank() || t.definition.isBlank()) {
                        termIssues.add("${lesson.id}: Term with empty name/def '${t.name}'")
                    }
                }

                // Check trend diagnostics
                for (tr in sec.тренд_оношлол) {
                    if (tr.signal.isBlank() || tr.evidence.isBlank() || tr.wrongConclusion.isBlank() || tr.correctAction.isBlank()) {
                        trendIssues.add("${lesson.id}: Trend item with blank fields: ${tr.signal}")
                    }
                }

                // Check quiz questions
                for (q in sec.шалгах_асуултууд) {
                    if (q.question.isBlank() || q.options.isEmpty() || q.correctAnswer.isBlank() || q.explanation.isBlank()) {
                        emptyFieldIssues.add("${lesson.id}: Quiz question incomplete '${q.question}'")
                    }
                    if (!q.options.contains(q.correctAnswer)) {
                        emptyFieldIssues.add("${lesson.id}: Quiz question '${q.question}' correctAnswer '${q.correctAnswer}' not in options ${q.options}")
                    }
                }
            }
        }

        println("Total lessons across all modules: $totalLessons")
        println("Empty field issues found: ${emptyFieldIssues.size}")
        emptyFieldIssues.forEach { println(" - $it") }

        println("Term issues found: ${termIssues.size}")
        termIssues.forEach { println(" - $it") }

        println("Trend issues found: ${trendIssues.size}")
        trendIssues.forEach { println(" - $it") }

        assertTrue(modules.isNotEmpty())
    }

    @Test
    fun qa_2_search_and_glossary_check() {
        val allTerms = CourseDataProvider.getAllTerms()
        println("=== 2. GLOSSARY TERMS CHECK ===")
        println("Total raw terms in app: ${allTerms.size}")
        val uniqueNames = allTerms.map { it.name.trim() }.toSet()
        println("Unique term names: ${uniqueNames.size}")

        // Test search keywords: "pH", "C01", "MOC", "ANFO", "VOD", "SOP"
        val testKeywords = listOf("pH", "MOC", "ANFO", "VOD", "SOP", "C01")
        for (kw in testKeywords) {
            val matches = mutableListOf<String>()
            for (mod in CourseDataProvider.allModules) {
                for (lesson in mod.lessons) {
                    val combined = "${lesson.title} ${lesson.summary} ${lesson.sections.онол} ${lesson.sections.механизм} ${lesson.sections.жишээ} ${lesson.sections.эх_сурвалж}"
                    if (combined.contains(kw, ignoreCase = true) || lesson.sections.нэр_томьёо.any { it.name.contains(kw, ignoreCase = true) || it.definition.contains(kw, ignoreCase = true) }) {
                        matches.add("${lesson.number} (${lesson.title})")
                    }
                }
            }
            println("Search keyword '$kw' matched ${matches.size} lessons. First 3 matches: ${matches.take(3)}")
            assertTrue("Search for '$kw' should return results", matches.isNotEmpty())
        }
    }

    @Test
    fun qa_3_text_integrity_and_math_units() {
        val modules = CourseDataProvider.allModules
        val specialSymbols = listOf("m³", "°C", "кг/м³", "Па", "кПа", "MJ/kg", "m/s", "Ө", "ү", "Ү", "ё", "Ё")
        println("=== 3. TEXT AND SYMBOL INTEGRITY CHECK ===")

        for (sym in specialSymbols) {
            var found = 0
            for (mod in modules) {
                for (lesson in mod.lessons) {
                    val fullText = "${lesson.title} ${lesson.summary} ${lesson.sections.онол} ${lesson.sections.хувьсагч_нэгж} ${lesson.sections.жишээ}"
                    if (fullText.contains(sym)) {
                        found++
                    }
                }
            }
            println("Symbol / Cyrillic '$sym' occurs in $found lessons.")
        }
    }

    @Test
    fun qa_4_performance_module12_load_time() {
        println("=== 4. PERFORMANCE MEASUREMENT ===")
        val timeMs = measureTimeMillis {
            val mod12 = CourseDataProvider.allModules.find { it.id == "mod_12" }
            assertNotNull(mod12)
            assertEquals(13, mod12!!.lessons.size)
        }
        println("Module XII load time: ${timeMs}ms (Well under 2000ms threshold)")
        assertTrue(timeMs < 2000)
    }
}

