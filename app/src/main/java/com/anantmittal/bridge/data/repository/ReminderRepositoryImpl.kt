package com.anantmittal.bridge.data.repository

import com.anantmittal.bridge.data.local.ReminderDao
import com.anantmittal.bridge.data.local.toDomainModel
import com.anantmittal.bridge.data.local.toEntity
import com.anantmittal.bridge.domain.model.Reminder
import com.anantmittal.bridge.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(
    private val dao: ReminderDao
) : ReminderRepository {
    override fun getReminders(): Flow<List<Reminder>> {
        return dao.getReminders().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return dao.getReminderById(id)?.toDomainModel()
    }

    override suspend fun addReminder(reminder: Reminder) {
        dao.insertReminder(reminder.toEntity())
    }

    override suspend fun updateReminder(reminder: Reminder) {
        dao.updateReminder(reminder.toEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        dao.deleteReminder(reminder.toEntity())
    }
}
