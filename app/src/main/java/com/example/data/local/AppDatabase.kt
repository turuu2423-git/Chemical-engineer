package com.example.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val moduleId: String,
    val isCompleted: Boolean,
    val completedAt: Long? = null,
    val lastReadAt: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false
)

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey val quizId: String,
    val lessonId: String,
    val score: Int,
    val totalQuestions: Int,
    val attemptedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_notes")
data class UserNoteEntity(
    @PrimaryKey val lessonId: String,
    val noteText: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Dao
interface AppDao {
    @Query("SELECT * FROM lesson_progress")
    fun getAllProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getProgressForLesson(lessonId: String): LessonProgressEntity?

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    fun observeProgressForLesson(lessonId: String): Flow<LessonProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: LessonProgressEntity)

    @Query("UPDATE lesson_progress SET isCompleted = :completed, completedAt = :completedAt WHERE lessonId = :lessonId")
    suspend fun updateLessonCompletion(lessonId: String, completed: Boolean, completedAt: Long?)

    @Query("UPDATE lesson_progress SET isBookmarked = :isBookmarked WHERE lessonId = :lessonId")
    suspend fun updateBookmark(lessonId: String, isBookmarked: Boolean)

    @Query("SELECT * FROM quiz_attempts")
    fun getAllQuizAttempts(): Flow<List<QuizAttemptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(attempt: QuizAttemptEntity)

    @Query("SELECT * FROM user_notes WHERE lessonId = :lessonId")
    fun getNoteForLesson(lessonId: String): Flow<UserNoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: UserNoteEntity)

    @Query("DELETE FROM lesson_progress")
    suspend fun clearAllProgress()

    @Query("DELETE FROM quiz_attempts")
    suspend fun clearAllQuizAttempts()
}

@Database(
    entities = [
        LessonProgressEntity::class,
        QuizAttemptEntity::class,
        UserNoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
