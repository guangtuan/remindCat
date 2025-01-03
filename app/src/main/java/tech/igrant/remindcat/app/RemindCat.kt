package tech.igrant.remindcat.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tech.igrant.filedb.retrofitdriven.FileDrivenDb
import tech.igrant.remindcat.reminder.ReminderManager

class RemindCat : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RemindCat)
            modules(
                module {
                    single {
                        FileDrivenDb(applicationContext.cacheDir)
                    }
                    single {
                        ReminderManager.createMe(get())
                    }
                }
            )
        }
    }

}