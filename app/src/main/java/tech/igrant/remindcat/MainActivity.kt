package tech.igrant.remindcat


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun ReminderList(reminders: List<Reminder>, onAddReminder: () -> Unit) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("添加提醒") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add Reminder") },
                    onClick = onAddReminder
                )
            },
            content = { padding ->
                LazyColumn(contentPadding = padding) {
                    items(reminders) { item -> ReminderItem(item) }
                }
            }
        )
    }

    @Composable
    fun ReminderItem(reminder: Reminder) {
        Card {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = reminder.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "每${reminder.frequency}天", style = MaterialTheme.typography.titleMedium)
            }
        }
    }

    @Composable
    fun AddReminderDialog(onDismiss: () -> Unit, onAdd: (Reminder) -> Unit) {
        var name by remember { mutableStateOf("") }
        var frequency by remember { mutableStateOf("" )}

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("添加提醒") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("提醒名称") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = null)
                    )
                    TextField(
                        value = frequency,
                        onValueChange = { frequency = it },
                        label = { Text("频率（天）") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            // 当用户完成输入时，提交表单
                            try {
                                val freq = frequency.toInt()
                                onAdd(Reminder(name, freq))
                                onDismiss()
                            } catch (e: NumberFormatException) {
                                // 处理频率输入非法的情况
                            }
                        })
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    try {
                        val freq = frequency.toInt()
                        onAdd(Reminder(name, freq))
                        onDismiss()
                    } catch (e: NumberFormatException) {
                        // 处理频率输入非法的情况
                    }
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
            }
        )
    }

    @Composable
    fun MainScreen() {
        val (showAddReminderDialog, setShowAddReminderDialog) = remember { mutableStateOf(false) }
        val reminders = remember { mutableListOf(
            Reminder(name = "换水", frequency = 3),
            Reminder(name = "煮鸡胸肉", frequency = 2)
        ) }

        if (showAddReminderDialog) {
            AddReminderDialog(onDismiss = { setShowAddReminderDialog(false) }) { reminder ->
                // 这里可以更新提醒列表
                reminders.add(reminder)
                setShowAddReminderDialog(false)
            }
        }

        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("添加提醒") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add Reminder") },
                    onClick = { setShowAddReminderDialog(true) }
                )
            },
            content = { padding: PaddingValues ->
                Text("Hello, Compose!", modifier = Modifier.padding(padding))
                ReminderList(reminders, onAddReminder = { setShowAddReminderDialog(true) })
            }
        )
    }

}