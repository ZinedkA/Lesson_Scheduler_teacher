package com.example.ozelderstakip.domain.repository

import com.example.ozelderstakip.data.local.dao.StudentDao
import com.example.ozelderstakip.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudents(): Flow<List<StudentEntity>> = studentDao.getAllStudents()
    suspend fun getStudentById(id: Int): StudentEntity? = studentDao.getStudentById(id)
    suspend fun insertStudent(student: StudentEntity): Long = studentDao.insertStudent(student)
    suspend fun updateStudent(student: StudentEntity) = studentDao.updateStudent(student)
    suspend fun deleteStudent(student: StudentEntity) = studentDao.deleteStudent(student)
}
