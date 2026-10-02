package com.example.ozelderstakip.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ozelderstakip.data.local.entity.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons WHERE startTime >= :start AND startTime <= :end ORDER BY startTime ASC")
    fun getLessonsBetweenDates(start: Long, end: Long): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE studentId = :studentId ORDER BY startTime DESC")
    fun getLessonsByStudent(studentId: Int): Flow<List<LessonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Delete
    suspend fun deleteLesson(lesson: LessonEntity)

    @Query("DELETE FROM lessons WHERE recurringGroupId = :groupId")
    suspend fun deleteRecurringLessons(groupId: String)
}
