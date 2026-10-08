package com.anantmittal.bridge.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anantmittal.bridge.domain.model.Reminder
import com.anantmittal.bridge.domain.repository.AlarmScheduler
import com.anantmittal.bridge.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        repository.getReminders().onEach { reminders ->
            _state.update { it.copy(reminders = reminders) }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnAddFabClicked -> {
                _state.update { it.copy(showAddDialog = true) }
            }

            HomeAction.OnDismissDialog -> {
                _state.update { it.copy(showAddDialog = false, newReminderTitle = "") }
            }

            is HomeAction.OnTimeChange -> {
                _state.update { it.copy(newReminderHour = action.hour, newReminderMinute = action.minute) }
            }

            is HomeAction.OnTitleChange -> {
                _state.update { it.copy(newReminderTitle = action.title) }
            }

            HomeAction.OnSaveReminder -> {
                viewModelScope.launch {
                    val currentState = _state.value
                    if (currentState.newReminderTitle.isNotBlank()) {
                        val reminder = Reminder(
                            title = currentState.newReminderTitle,
                            hour = currentState.newReminderHour,
                            minute = currentState.newReminderMinute
                        )
                        repository.addReminder(reminder)
                        alarmScheduler.schedule(reminder)
                        _state.update { it.copy(showAddDialog = false, newReminderTitle = "") }
                    }
                }
            }

            is HomeAction.OnDeleteReminder -> {
                viewModelScope.launch {
                    repository.deleteReminder(action.reminder)
                    alarmScheduler.cancel(action.reminder)
                }
            }

            is HomeAction.OnToggleReminder -> {
                viewModelScope.launch {
                    val updated = action.reminder.copy(isEnabled = action.isEnabled)
                    repository.updateReminder(updated)
                    if (action.isEnabled) {
                        alarmScheduler.schedule(updated)
                    } else {
                        alarmScheduler.cancel(updated)
                    }
                }
            }
        }
    }
}