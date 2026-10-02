package com.example.ozelderstakip.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ozelderstakip.presentation.calendar.CalendarViewModel
import com.example.ozelderstakip.presentation.calendar.WeeklyCalendarScreen
import com.example.ozelderstakip.presentation.finance.FinanceScreen
import com.example.ozelderstakip.presentation.finance.FinanceViewModel
import com.example.ozelderstakip.presentation.settings.SettingsScreen
import com.example.ozelderstakip.presentation.student.StudentListScreen
import com.example.ozelderstakip.presentation.student.StudentViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel,
    studentViewModel: StudentViewModel,
    financeViewModel: FinanceViewModel
) {
    NavHost(navController = navController, startDestination = "calendar") {
        composable("calendar") {
            WeeklyCalendarScreen(
                viewModel = calendarViewModel,
                onAddLessonClicked = {
                    // Navigate to add lesson
                }
            )
        }
        composable("students") {
            StudentListScreen(viewModel = studentViewModel)
        }
        composable("finance") {
            FinanceScreen(viewModel = financeViewModel)
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}
