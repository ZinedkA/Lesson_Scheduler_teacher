package com.example.ozelderstakip.presentation.finance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozelderstakip.data.local.entity.StudentEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(viewModel: FinanceViewModel) {
    val students by viewModel.students.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finansal Durum") }
            )
        }
    ) { paddingValues ->
        if (students.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Finansal kayıt bulunamadı.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(students) { student ->
                    FinanceCard(student = student)
                }
            }
        }
    }
}

@Composable
fun FinanceCard(student: StudentEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = student.studentName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Saatlik Ücret: ${student.defaultHourlyRate} TL", fontSize = 14.sp)
            }
            Button(onClick = { /* TODO: Ödeme Al ekranına git */ }) {
                Text("Ödeme Al")
            }
        }
    }
}
