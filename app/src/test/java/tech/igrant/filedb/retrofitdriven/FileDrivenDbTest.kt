package tech.igrant.filedb.retrofitdriven

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import tech.igrant.fundation.id.Random
import tech.igrant.remindcat.reminder.Reminder
import tech.igrant.remindcat.reminder.ReminderManager
import java.io.File

class FileDrivenDbTest {

    @Test
    fun createService() {
        val buildDir = File(System.getProperty("buildDir")!!)
        println("Build directory: $buildDir")
        val sandbox = File(File(buildDir, "tmp"), Random.id())
        println("sandBox: $sandbox")
        val service = FileDrivenDb(sandbox).createService(ReminderManager::class.java)
        val saved = runBlocking {
            service.save(Reminder("a", 5))
        }
        Assertions.assertNotNull(saved)
        val list = runBlocking {
            service.list()
        }
        Assertions.assertNotNull(list)
        Assertions.assertEquals(1, list.size)
    }

}