package com.anantmittal.bridge.presentation.home_screen

import com.anantmittal.bridge.domain.model.Reminder

sealed interface HomeAction {
    data object OnAddFabClicked : HomeAction
    data object OnDismissDialog : HomeAction
    data object OnSaveReminder : HomeAction
    data class OnTitleChange(val title: String) : HomeAction
    data class OnTimeChange(val hour: Int, val minute: Int) : HomeAction
    data class OnToggleReminder(val reminder: Reminder, val isEnabled: Boolean) : HomeAction
    data class OnDeleteReminder(val reminder: Reminder) : HomeAction
}