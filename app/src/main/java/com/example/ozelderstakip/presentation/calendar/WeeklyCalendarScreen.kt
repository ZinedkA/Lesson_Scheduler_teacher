package com.example.ozelderstakip.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeeklyCalendarScreen(
    viewModel: CalendarViewModel,
    onAddLessonClicked: (startTime: Long) -> Unit
) {
    val lessons by viewModel.lessons.collectAsState()
    val weekStart by viewModel.currentWeekStart.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Week Header (e.g. Navigation)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "< Önceki", modifier = Modifier.clickable { viewModel.previousWeek() })
            Text(
                text = "Hafta: ${formatDate(weekStart)}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "Sonraki >", modifier = Modifier.clickable { viewModel.nextWeek() })
        }

        // Days Header
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(50.dp)) // Time column space
            for (i in 0..6) {
                val dayTime = weekStart + (i * 24 * 60 * 60 * 1000L)
                Text(
                    text = getDayName(dayTime),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Calendar Grid
        val scrollState = rememberScrollState()
        Box(modifier = Modifier.verticalScroll(scrollState)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Time Labels
                Column(modifier = Modifier.width(50.dp)) {
                    for (hour in 8..22) { // 08:00 - 22:00
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(text = "$hour:00", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                // Grid Cells
                for (dayIndex in 0..6) {
                    Column(modifier = Modifier.weight(1f)) {
                        for (hour in 8..22) {
                            val cellStartTime = weekStart + (dayIndex * 24 * 60 * 60 * 1000L) + (hour * 60 * 60 * 1000L)
                            Box(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .border(0.5.dp, Color.LightGray)
                                    .clickable {
                                        onAddLessonClicked(cellStartTime)
                                    }
                            ) {
                                // Find lesson for this slot
                                val lesson = lessons.find { 
                                    it.startTime >= cellStartTime && it.startTime < cellStartTime + (60 * 60 * 1000L)
                                }
                                if (lesson != null) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(2.dp)
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Text(
                                            text = "Ders", // Geliştirilecek: Öğrenci adı gösterilebilir
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatDate(timeInMillis: Long): String {
    val formatter = SimpleDateFormat("dd MMM", Locale("tr"))
    return formatter.format(timeInMillis)
}

private fun getDayName(timeInMillis: Long): String {
    val formatter = SimpleDateFormat("EEE", Locale("tr"))
    return formatter.format(timeInMillis)
}
