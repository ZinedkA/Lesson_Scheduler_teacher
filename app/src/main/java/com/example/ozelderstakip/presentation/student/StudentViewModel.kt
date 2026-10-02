package com.example.ozelderstakip.presentation.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ozelderstakip.data.local.entity.StudentEntity
import com.example.ozelderstakip.domain.repository.StudentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    val students: StateFlow<List<StudentEntity>> = repository.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addStudent(studentNumber: String, studentName: String, parentName: String, parentPhone: String, defaultRate: Double) {
        viewModelScope.launch {
            repository.insertStudent(
                StudentEntity(
                    studentNumber = studentNumber,
                    studentName = studentName,
                    parentName = parentName,
                    parentPhone = parentPhone,
                    defaultHourlyRate = defaultRate
                )
            )
        }
    }

    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }
}

class StudentViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
