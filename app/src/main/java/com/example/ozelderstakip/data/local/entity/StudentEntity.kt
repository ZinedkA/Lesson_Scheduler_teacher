package com.example.ozelderstakip.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val studentId: Int = 0,
    val studentNumber: String,
    val studentName: String,
    val parentName: String,
    val parentPhone: String,
    val defaultHourlyRate: Double,
    val createdAt: Long = System.currentTimeMillis()
)
