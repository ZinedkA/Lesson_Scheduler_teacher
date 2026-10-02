package com.example.ozelderstakip.domain.repository

import com.example.ozelderstakip.data.local.dao.PaymentDao
import com.example.ozelderstakip.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

class PaymentRepository(private val paymentDao: PaymentDao) {
    fun getPaymentsByStudent(studentId: Int): Flow<List<PaymentEntity>> = paymentDao.getPaymentsByStudent(studentId)
    suspend fun insertPayment(payment: PaymentEntity): Long = paymentDao.insertPayment(payment)
    suspend fun updatePayment(payment: PaymentEntity) = paymentDao.updatePayment(payment)
    suspend fun deletePayment(payment: PaymentEntity) = paymentDao.deletePayment(payment)
}
