package com.example.ozelderstakip.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ozelderstakip.data.local.entity.LessonEntity
import com.example.ozelderstakip.domain.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class CalendarViewModel(private val repository: LessonRepository) : ViewModel() {

    private val _lessons = MutableStateFlow<List<LessonEntity>>(emptyList())
    val lessons: StateFlow<List<LessonEntity>> = _lessons.asStateFlow()

    private val _currentWeekStart = MutableStateFlow<Long>(0L)
    val currentWeekStart: StateFlow<Long> = _currentWeekStart.asStateFlow()

    init {
        // Uygulama açıldığında içinde bulunduğumuz haftanın pazartesi gününü bul
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        _currentWeekStart.value = calendar.timeInMillis
        loadLessonsForWeek(calendar.timeInMillis)
    }

    private fun loadLessonsForWeek(startOfWeek: Long) {
        val endOfWeek = startOfWeek + (7 * 24 * 60 * 60 * 1000L) - 1
        viewModelScope.launch {
            repository.getLessonsBetweenDates(startOfWeek, endOfWeek).collect { lessonList ->
                _lessons.value = lessonList
            }
        }
    }

    fun nextWeek() {
        val next = _currentWeekStart.value + (7 * 24 * 60 * 60 * 1000L)
        _currentWeekStart.value = next
        loadLessonsForWeek(next)
    }

    fun previousWeek() {
        val prev = _currentWeekStart.value - (7 * 24 * 60 * 60 * 1000L)
        _currentWeekStart.value = prev
        loadLessonsForWeek(prev)
    }
}

class CalendarViewModelFactory(private val repository: LessonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
