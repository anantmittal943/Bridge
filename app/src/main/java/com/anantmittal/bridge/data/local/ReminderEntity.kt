package com.anantmittal.bridge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anantmittal.bridge.domain.model.Reminder

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val title: String,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean
)

fun ReminderEntity.toDomainModel() = Reminder(
    id = id,
    title = title,
    hour = hour,
    minute = minute,
    isEnabled = isEnabled
)

fun Reminder.toEntity() = ReminderEntity(
    id = id,
    title = title,
    hour = hour,
    minute = minute,
    isEnabled = isEnabled
)
