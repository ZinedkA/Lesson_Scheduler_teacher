package com.example.ozelderstakip.presentation.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
fun StudentListScreen(viewModel: StudentViewModel) {
    val students by viewModel.students.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Öğrenciler") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Öğrenci Ekle")
            }
        }
    ) { paddingValues ->
        if (students.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Henüz kayıtlı öğrenci yok.")
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
                    StudentCard(student = student, onDelete = { viewModel.deleteStudent(student) })
                }
            }
        }

        if (showAddDialog) {
            AddStudentDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { number, name, parentName, parentPhone, rate ->
                    viewModel.addStudent(number, name, parentName, parentPhone, rate)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun StudentCard(student: StudentEntity, onDelete: () -> Unit) {
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
                Text(text = "No: ${student.studentNumber}", fontSize = 14.sp)
                Text(text = "Veli: ${student.parentName} (${student.parentPhone})", fontSize = 14.sp)
                Text(text = "Ücret: ${student.defaultHourlyRate} TL", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun AddStudentDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, String, Double) -> Unit
) {
    var studentNumber by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var parentPhone by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Yeni Öğrenci Ekle") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = studentNumber, onValueChange = { studentNumber = it }, label = { Text("Öğrenci No") })
                OutlinedTextField(value = studentName, onValueChange = { studentName = it }, label = { Text("Öğrenci Adı") })
                OutlinedTextField(value = parentName, onValueChange = { parentName = it }, label = { Text("Veli Adı") })
                OutlinedTextField(value = parentPhone, onValueChange = { parentPhone = it }, label = { Text("Veli Telefon") })
                OutlinedTextField(value = rate, onValueChange = { rate = it }, label = { Text("Saatlik Ücret (TL)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val rateDouble = rate.toDoubleOrNull() ?: 0.0
                onAdd(studentNumber, studentName, parentName, parentPhone, rateDouble)
            }) {
                Text("Ekle")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}
