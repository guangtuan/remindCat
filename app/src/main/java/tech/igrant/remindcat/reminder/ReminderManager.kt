package tech.igrant.remindcat.reminder

import retrofit2.http.GET
import retrofit2.http.POST
import tech.igrant.filedb.retrofitdriven.FileDrivenDb

interface ReminderManager {

    @GET("reminders")
    suspend fun list(): List<Reminder>

    @POST("reminders")
    suspend fun save(reminder: Reminder): Reminder

    companion object {
        fun createMe(fileDrivenDb: FileDrivenDb): ReminderManager {
            return fileDrivenDb.createService(ReminderManager::class.java)
        }
    }

}
