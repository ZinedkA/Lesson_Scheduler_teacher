package com.example.ozelderstakip.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("studentId")]
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val lessonId: Int = 0,
    val studentId: Int,
    val startTime: Long,
    val endTime: Long,
    val status: String, // SCHEDULED, COMPLETED, CANCELLED
    val cancelledBy: String, // NONE, TUTOR, STUDENT
    val cancelReason: String,
    val price: Double,
    val paymentStatus: String, // PENDING, PAID, REFUNDED
    val lessonNotes: String,
    val recurringGroupId: String
)
