package com.example.ozelderstakip.presentation.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ozelderstakip.data.local.entity.StudentEntity
import com.example.ozelderstakip.domain.repository.PaymentRepository
import com.example.ozelderstakip.domain.repository.StudentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FinanceViewModel(
    private val paymentRepository: PaymentRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {

    // Şimdilik sadece öğrencileri listeliyoruz, detaylı finans modülü sonra geliştirilebilir
    val students: StateFlow<List<StudentEntity>> = studentRepository.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}

class FinanceViewModelFactory(
    private val paymentRepository: PaymentRepository,
    private val studentRepository: StudentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinanceViewModel(paymentRepository, studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
