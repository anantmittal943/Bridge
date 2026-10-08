package com.anantmittal.bridge.di

import androidx.room.Room
import com.anantmittal.bridge.data.alarm.AndroidAlarmScheduler
import com.anantmittal.bridge.data.local.ReminderDatabase
import com.anantmittal.bridge.data.repository.ReminderRepositoryImpl
import com.anantmittal.bridge.domain.repository.AlarmScheduler
import com.anantmittal.bridge.domain.repository.ReminderRepository
import com.anantmittal.bridge.presentation.home_screen.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ReminderDatabase::class.java,
            "reminders.db"
        ).build()
    }

    single { get<ReminderDatabase>().dao }

    single<ReminderRepository> { ReminderRepositoryImpl(get()) }
    single<AlarmScheduler> { AndroidAlarmScheduler(androidContext()) }
    
    viewModelOf(::HomeViewModel)
}
