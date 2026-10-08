package com.anantmittal.bridge.presentation.home_screen

import com.anantmittal.bridge.domain.model.Reminder

data class HomeState(
    val reminders: List<Reminder> = emptyList(),
    val showAddDialog: Boolean = false,
    val newReminderTitle: String = "",
    val newReminderHour: Int = 8,
    val newReminderMinute: Int = 0
)