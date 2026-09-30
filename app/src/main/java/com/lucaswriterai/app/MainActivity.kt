package com.lucaswriterai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LucasWriterApp() }
    }
}

@Composable
fun LucasWriterApp() {
    var text by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("LucasWriterAI готов.") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("LucasWriterAI", style = MaterialTheme.typography.headlineMedium)
                Text("Локальное приложение для собственного ИИ-писателя.")
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxSize().weight(1f),
                    label = { Text("Запрос") }
                )
                Button(onClick = {
                    result = if (text.isBlank()) "Введите запрос." else "Запрос принят: $text"
                }) {
                    Text("Запустить")
                }
                Text(result)
            }
        }
    }
}
