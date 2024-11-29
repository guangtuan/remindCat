package tech.igrant.filedb.retrofitdriven

import org.junit.jupiter.api.Assertions
import tech.igrant.remindcat.reminder.ReminderProvider

class RetrofitExtTest {


    @org.junit.jupiter.api.Test
    fun parse() {
        (ReminderProvider::class.java
            .methods
            .firstOrNull { it.name == "list" }
            ?.let { method ->
                val (httpMethod, path) = RetrofitExt.parse(method)
                Assertions.assertEquals(
                    HttpMethod.GET,
                    httpMethod
                )
                Assertions.assertEquals(
                    "reminders",
                    path
                )
            })
            ?: error("Method not found")
    }

}