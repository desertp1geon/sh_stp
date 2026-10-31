package com.example.sh_stp.basic.koin

import android.content.Context
import androidx.room.Room
import com.example.sh_stp.data.MotorcycleDatabase
import com.example.sh_stp.data.MotorcycleRepository
import com.example.sh_stp.data.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "motorcycles_database"
        ).build()
    }

    single<MotorcycleRepository> {
        MotorcycleDatabase(get())
    }
}

fun startKoinApplication(context: Context) {
    startKoin {
        androidContext(context.applicationContext)
        modules(appModule)
    }
}