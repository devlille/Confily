package org.gdglille.devfest.android.theme.m3.schedules.feature

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.gdglille.devfest.AlarmScheduler
import org.gdglille.devfest.models.ui.AgendaUi
import org.gdglille.devfest.models.ui.TalkItemUi
import org.gdglille.devfest.repositories.AgendaRepository

sealed class AgendaUiState {
    data class Loading(val agenda: ImmutableList<AgendaUi>) : AgendaUiState()
    data class Success(val agenda: ImmutableList<AgendaUi>) : AgendaUiState()
    data class Failure(val throwable: Throwable) : AgendaUiState()
}

@FlowPreview
@ExperimentalCoroutinesApi
class AgendaViewModel(
    private val repository: AgendaRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    private val _uiState = MutableStateFlow<AgendaUiState>(
        AgendaUiState.Loading(persistentListOf(AgendaUi.fake))
    )
    val uiState: StateFlow<AgendaUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                repository.agenda().collect {
                    if (it.isNotEmpty()) {
                        _uiState.value = AgendaUiState.Success(it.values.toImmutableList())
                    }
                }
            } catch (error: Throwable) {
                Firebase.crashlytics.recordException(error)
                _uiState.value = AgendaUiState.Failure(error)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun markAsFavorite(context: Context, talkItem: TalkItemUi) = viewModelScope.launch {
        alarmScheduler.schedule(context, talkItem)
    }

    object Factory {
        fun create(
            repository: AgendaRepository,
            alarmScheduler: AlarmScheduler
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = AgendaViewModel(
                repository = repository,
                alarmScheduler = alarmScheduler
            ) as T
        }
    }
}