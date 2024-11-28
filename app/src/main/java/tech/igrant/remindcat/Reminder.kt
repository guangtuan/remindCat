package tech.igrant.remindcat

data class Reminder(
    val name: String,
    val frequency: Int // 频率（每n天）
)