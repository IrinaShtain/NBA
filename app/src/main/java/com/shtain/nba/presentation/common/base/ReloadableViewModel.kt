package com.shtain.nba.presentation.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

abstract class ReloadableViewModel : ViewModel() {

    protected val refreshTrigger = MutableStateFlow(Trigger)

    fun reload() {
        refreshTrigger.tryEmit(Trigger)
    }

    fun <T : Any?> Flow<T>.reloadWhenRefresh(): Flow<T> {
        return combine(this, refreshTrigger) { data, _ -> data }
    }
}

/**
 * Special trigger object to work with a "distinct until changed" logic
 */
@Suppress("EqualsOrHashCode")
object Trigger {
    override fun equals(other: Any?): Boolean = false
}