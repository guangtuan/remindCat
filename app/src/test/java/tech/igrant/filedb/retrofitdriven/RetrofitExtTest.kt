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
                val result = RetrofitExt.parse(method, emptyArray())
                if (result is Get) {
                    println(result.resultType)
                } else {
                    Assertions.fail("it should be parse to Get")
                }
            })
            ?: error("Method not found")
    }

}