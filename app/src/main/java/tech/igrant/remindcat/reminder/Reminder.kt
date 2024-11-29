package tech.igrant.remindcat.reminder

data class Reminder(
    val name: String,
    val frequency: Int // 频率（每n天）
)