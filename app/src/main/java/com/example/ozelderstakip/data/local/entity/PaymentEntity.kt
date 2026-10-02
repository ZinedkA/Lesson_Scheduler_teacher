package com.example.ozelderstakip.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
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
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Int = 0,
    val studentId: Int,
    val amount: Double,
    val paymentDate: Long,
    val notes: String
)
