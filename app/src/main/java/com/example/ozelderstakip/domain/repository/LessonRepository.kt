package com.example.ozelderstakip.domain.repository

import com.example.ozelderstakip.data.local.dao.LessonDao
import com.example.ozelderstakip.data.local.entity.LessonEntity
import kotlinx.coroutines.flow.Flow

class LessonRepository(private val lessonDao: LessonDao) {
    
    fun getLessonsBetweenDates(start: Long, end: Long): Flow<List<LessonEntity>> {
        return lessonDao.getLessonsBetweenDates(start, end)
    }

    suspend fun insertLesson(lesson: LessonEntity) {
        lessonDao.insertLesson(lesson)
    }

    suspend fun insertLessons(lessons: List<LessonEntity>) {
        lessonDao.insertLessons(lessons)
    }

    suspend fun updateLesson(lesson: LessonEntity) {
        lessonDao.updateLesson(lesson)
    }

    suspend fun deleteLesson(lesson: LessonEntity) {
        lessonDao.deleteLesson(lesson)
    }
}
