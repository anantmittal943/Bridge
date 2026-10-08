package com.anantmittal.bridge.domain.repository

import com.anantmittal.bridge.domain.model.Reminder

interface AlarmScheduler {
    fun schedule(reminder: Reminder)
    fun cancel(reminder: Reminder)
}
