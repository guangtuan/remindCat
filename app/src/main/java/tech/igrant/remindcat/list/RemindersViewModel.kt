package tech.igrant.remindcat.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.igrant.remindcat.reminder.Reminder
import tech.igrant.remindcat.reminder.ReminderManager

class RemindersViewModel(private val reminderManager: ReminderManager) : ViewModel() {
    private val _uiState = MutableStateFlow(RemindersState())
    val uiState: StateFlow<RemindersState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            try {
                val fetchedReminders = reminderManager.list()
                _uiState.value = _uiState.value.copy(
                    reminders = fetchedReminders,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("RemindersViewModel", "Failed to fetch reminders", e)
            }
        }
    }

    fun showAddReminderDialog() {
        _uiState.value = _uiState.value.copy(showAddReminderDialog = true)
    }

    fun hideAddReminderDialog() {
        _uiState.value = _uiState.value.copy(showAddReminderDialog = false)
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            val savedReminder = reminderManager.save(reminder)
            _uiState.value = _uiState.value.copy(
                reminders = _uiState.value.reminders + savedReminder,
                showAddReminderDialog = false
            )
        }
    }
}