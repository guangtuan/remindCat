package tech.igrant.filedb.retrofitdriven

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tech.igrant.remindcat.reminder.Reminder
import tech.igrant.remindcat.reminder.ReminderProvider
import java.io.File

class FileDrivenDbTest {

    @Test
    fun createService() {
        val buildDir = File(System.getProperty("buildDir")!!)
        println("Build directory: $buildDir")
        val sandBox = File(buildDir, "tmp")
        println("sandBox: $sandBox")
        val service = FileDrivenDb(RequestHandler(sandBox)).createService(ReminderProvider::class.java)
        val saved = runBlocking {
            service.save(Reminder("a", 5))
        }
        Assertions.assertNotNull(saved)
    }

}