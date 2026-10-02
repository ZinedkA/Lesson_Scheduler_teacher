package com.example.ozelderstakip

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.ozelderstakip.data.local.AppDatabase
import com.example.ozelderstakip.domain.repository.LessonRepository
import com.example.ozelderstakip.domain.repository.PaymentRepository
import com.example.ozelderstakip.domain.repository.StudentRepository
import com.example.ozelderstakip.presentation.calendar.CalendarViewModel
import com.example.ozelderstakip.presentation.calendar.CalendarViewModelFactory
import com.example.ozelderstakip.presentation.finance.FinanceViewModel
import com.example.ozelderstakip.presentation.finance.FinanceViewModelFactory
import com.example.ozelderstakip.presentation.student.StudentViewModel
import com.example.ozelderstakip.presentation.student.StudentViewModelFactory
import com.example.ozelderstakip.ui.theme.OzelDersTakipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Veritabanı ve Repository başlatılması
        val database = AppDatabase.getDatabase(this)
        
        val lessonRepository = LessonRepository(database.lessonDao())
        val calendarFactory = CalendarViewModelFactory(lessonRepository)
        val calendarViewModel = ViewModelProvider(this, calendarFactory)[CalendarViewModel::class.java]

        val studentRepository = StudentRepository(database.studentDao())
        val studentFactory = StudentViewModelFactory(studentRepository)
        val studentViewModel = ViewModelProvider(this, studentFactory)[StudentViewModel::class.java]

        val paymentRepository = PaymentRepository(database.paymentDao())
        val financeFactory = FinanceViewModelFactory(paymentRepository, studentRepository)
        val financeViewModel = ViewModelProvider(this, financeFactory)[FinanceViewModel::class.java]

        setContent {
            OzelDersTakipTheme {
                val navController = androidx.navigation.compose.rememberNavController()
                androidx.compose.material3.Scaffold(
                    bottomBar = {
                        androidx.compose.material3.NavigationBar {
                            androidx.compose.material3.NavigationBarItem(
                                icon = { androidx.compose.material3.Icon(androidx.compose.material.icons.Icons.Default.DateRange, contentDescription = "Takvim") },
                                label = { androidx.compose.material3.Text("Takvim") },
                                selected = false,
                                onClick = { navController.navigate("calendar") }
                            )
                            androidx.compose.material3.NavigationBarItem(
                                icon = { androidx.compose.material3.Icon(androidx.compose.material.icons.Icons.Default.Person, contentDescription = "Öğrenciler") },
                                label = { androidx.compose.material3.Text("Öğrenciler") },
                                selected = false,
                                onClick = { navController.navigate("students") }
                            )
                            androidx.compose.material3.NavigationBarItem(
                                icon = { androidx.compose.material3.Icon(androidx.compose.material.icons.Icons.Default.ShoppingCart, contentDescription = "Finans") }, // Temporary icon
                                label = { androidx.compose.material3.Text("Finans") },
                                selected = false,
                                onClick = { navController.navigate("finance") }
                            )
                            androidx.compose.material3.NavigationBarItem(
                                icon = { androidx.compose.material3.Icon(androidx.compose.material.icons.Icons.Default.Settings, contentDescription = "Ayarlar") },
                                label = { androidx.compose.material3.Text("Ayarlar") },
                                selected = false,
                                onClick = { navController.navigate("settings") }
                            )
                        }
                    }
                ) { innerPadding ->
                    androidx.compose.foundation.layout.Box(modifier = androidx.compose.foundation.layout.padding(innerPadding)) {
                        com.example.ozelderstakip.presentation.navigation.AppNavGraph(
                            navController = navController,
                            calendarViewModel = calendarViewModel,
                            studentViewModel = studentViewModel,
                            financeViewModel = financeViewModel
                        )
                    }
                }
            }
        }
    }
}
