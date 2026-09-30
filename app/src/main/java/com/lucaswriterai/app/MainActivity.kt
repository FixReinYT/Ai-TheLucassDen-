package com.lucaswriterai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

data class ChatMessage(val text: String, val fromUser: Boolean)

class LucasViewModel : ViewModel() {
    var messages by mutableStateOf(listOf(ChatMessage("LucasWriterAI готов. Локальное ядро пока в разработке.", false)))
        private set
    var rules by mutableStateOf("Пиши понятно, сохраняй контекст и не выдумывай факты.")

    fun send(text: String) {
        if (text.isBlank()) return
        messages = messages + ChatMessage(text.trim(), true) +
            ChatMessage("Принял запрос: ${text.trim()}", false)
    }

    fun newChat() { messages = emptyList() }
}

@Composable
fun LucasAITheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme(), content = content)
}

@Composable
fun ChatScreen(vm: LucasViewModel) {
    var input by remember { mutableStateOf("") }
    var settings by remember { mutableStateOf(false) }

    if (settings) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Настройки", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = vm.rules,
                onValueChange = { vm.rules = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Правила") }
            )
            Spacer(Modifier.height(16.dp))
            Button({ settings = false }) { Text("Назад") }
        }
        return
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("LucasWriterAI", style = MaterialTheme.typography.headlineSmall)
            Row {
                TextButton({ vm.newChat() }) { Text("Новый") }
                TextButton({ settings = true }) { Text("Правила") }
            }
        }
        LazyColumn(
            Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(vm.messages) { msg ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(msg.text, Modifier.padding(12.dp))
                }
            }
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Напиши запрос...") }
            )
            Spacer(Modifier.width(8.dp))
            Button({ vm.send(input); input = "" }) { Text("Отправить") }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LucasAITheme { ChatScreen(viewModel()) } }
    }
}
