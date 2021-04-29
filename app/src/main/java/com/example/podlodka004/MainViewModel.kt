package com.example.podlodka004

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

sealed class MessageEvent {
    object Idle : MessageEvent()
    object StarredOverflow : MessageEvent()
}

sealed class NavigationEvent {
    object Home : NavigationEvent()
    data class Details(val sessionId: String) : NavigationEvent()
    object Up : NavigationEvent()
}

class MainViewModel : ViewModel() {

    val navEvents = SingleLiveEvent<NavigationEvent>()

    private val _oneShotEvent = MutableLiveData<MessageEvent>(MessageEvent.Idle)
    val messages: LiveData<MessageEvent> = _oneShotEvent

    private val _starredSet = mutableSetOf<Session>()
    private val _starredSessions = MutableLiveData(listOf<Session>())
    val starredSessions: LiveData<List<Session>> = _starredSessions

    private val _searchString = MutableLiveData("")
    val searchString: LiveData<String> = _searchString

    private val _sessions = MutableLiveData(MockSessions)
    val filtered: LiveData<List<Session>> = _searchString.switchMap { query ->
        if (query.isNullOrBlank()) {
            liveData { emitSource(_sessions) }
        } else {
            liveData {
                emit(MockSessions.filter { session ->
                    session.speaker.toLowerCase(Locale.getDefault()).contains(query)
                            || session.description.toLowerCase(Locale.getDefault()).contains(query)
                })
            }
        }
    }

    fun toggleFavorite(session: Session) {
        if (!_starredSet.contains(session) && _starredSet.size >= 3) {
            showAchtungMessage()
            return
        }

        if (!_starredSet.add(session)) {
            _starredSet.remove(session)
        }
        _starredSessions.postValue(_starredSet.toList())
    }

    fun getSessionById(id: String) = MockSessions.first { it.id == id }

    fun searchStringChange(value: String) {
        _searchString.postValue(value.toLowerCase(Locale.getDefault()))
    }

    fun checkFavorite(session: Session) = _starredSessions.switchMap { starred ->
        liveData { emit(session in starred) }
    }

    fun sessionClick(session: Session) {
        navEvents.value = NavigationEvent.Details(session.id)
    }

    fun navigateUp() {
        navEvents.value = NavigationEvent.Up
    }

    private fun showAchtungMessage() = viewModelScope.launch {
        _oneShotEvent.postValue(MessageEvent.StarredOverflow)
        delay(1_500)
        _oneShotEvent.postValue(MessageEvent.Idle)
    }
}