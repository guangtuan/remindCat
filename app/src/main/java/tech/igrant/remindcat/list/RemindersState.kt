package tech.igrant.remindcat.list

import tech.igrant.remindcat.reminder.Reminder

data class RemindersState(
    val showAddReminderDialog: Boolean = false,
    val reminders: List<Reminder> = emptyList(),
    val isLoading: Boolean = true
)