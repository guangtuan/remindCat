package tech.igrant.remindcat.reminder

import retrofit2.http.GET
import retrofit2.http.POST

interface ReminderManager {

    @GET("reminders")
    suspend fun list(): List<Reminder>

    @POST("reminders")
    suspend fun save(reminder: Reminder): Reminder

}
