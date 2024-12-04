package tech.igrant.remindcat.list


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.android.ext.android.inject
import tech.igrant.remindcat.R
import tech.igrant.remindcat.reminder.Reminder
import tech.igrant.remindcat.reminder.ReminderManager

class RemindersPage : ComponentActivity() {

    private val reminderManager: ReminderManager by inject<ReminderManager>()

    private val viewModel: RemindersViewModel by lazy {
        RemindersViewModel(reminderManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun EachReminder(reminder: Reminder) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(getColor(R.color.card_background))
            ),
            shape = ShapeDefaults.Large,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color(getColor(R.color.text_primary)),
                    text = reminder.name,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = {},
                    colors = IconButtonColors(
                        containerColor = Color(getColor(R.color.button_primary)),
                        contentColor = Color(getColor(R.color.icon_primary)),
                        disabledContainerColor = Color(getColor(R.color.button_primary)),
                        disabledContentColor = Color(getColor(R.color.icon_primary)),
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color(getColor(R.color.text_secondary)),
                    text = "每${reminder.frequency}天",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    @Composable
    fun AddReminderDialog(onDismiss: () -> Unit, onAdd: (Reminder) -> Unit) {
        var name by remember { mutableStateOf("") }
        var frequency by remember { mutableStateOf("") }

        @Composable
        fun colors(): TextFieldColors {
            return TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(getColor(R.color.primary)),
                unfocusedIndicatorColor = Color(getColor(R.color.primary))
            )
        }

        AlertDialog(
            shape = RoundedCornerShape(0.dp),
            containerColor = Color(getColor(R.color.page_background)),
            onDismissRequest = onDismiss,
            title = { Text("添加提醒") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        color = Color(getColor(R.color.text_primary)),
                        text = "Title",
                        fontSize =  16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = name,
                        colors = colors(),
                        onValueChange = { name = it },
                        label = { Text("remind what") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = null)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        textAlign = TextAlign.Start,
                        color = Color(getColor(R.color.text_primary)),
                        text = "Freq",
                        fontSize =  16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = frequency,
                        colors = colors(),
                        onValueChange = { frequency = it },
                        label = { Text("频率（天）") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
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
                Button(
                    colors = ButtonColors(
                        containerColor = Color(getColor(R.color.button_primary)),
                        contentColor = Color(getColor(R.color.button_text_primary)),
                        disabledContainerColor = Color(getColor(R.color.button_primary)),
                        disabledContentColor = Color(getColor(R.color.button_text_primary)),
                    ),
                    onClick = {
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val uiState by viewModel.uiState.collectAsState()
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (uiState.showAddReminderDialog) {
                AddReminderDialog(
                    onDismiss = { viewModel.hideAddReminderDialog() },
                    onAdd = { reminder ->
                        viewModel.addReminder(reminder)
                    }
                )
            } else {
                Scaffold(
                    containerColor = Color(getColor(R.color.page_background)),
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = "ico",
                                    tint = Color.Black
                                )
                            },
                            colors = TopAppBarColors(
                                navigationIconContentColor = Color.Blue,
                                titleContentColor = Color(getColor(R.color.text_primary)),
                                actionIconContentColor = Color.Blue,
                                scrolledContainerColor = Color.Blue,
                                containerColor = Color.White,
                            ),
                            title = {
                                Text(
                                    text = getString(R.string.app_name),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            actions = {
                                Button(
                                    shape = RoundedCornerShape(8.dp),
                                    onClick = { viewModel.showAddReminderDialog() },
                                    colors = ButtonColors(
                                        containerColor = Color(getColor(R.color.button_primary)),
                                        contentColor = Color(getColor(R.color.button_text_primary)),
                                        disabledContainerColor = Color(getColor(R.color.button_primary)),
                                        disabledContentColor = Color(getColor(R.color.button_text_primary)),
                                    )
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            vertical = 4.dp,
                                            horizontal = 2.dp
                                        ),
                                        fontSize = TextUnit(16f, TextUnitType.Sp),
                                        text = "Add Reminder"
                                    )
                                }
                            }
                        )
                    },
                    content = { padding: PaddingValues ->
                        LazyColumn(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxWidth(),
                        ) {
                            items(uiState.reminders) { item -> EachReminder(item) }
                        }
                    }
                )
            }
        }
    }

}