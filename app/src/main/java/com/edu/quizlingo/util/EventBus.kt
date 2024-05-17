package com.edu.quizlingo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//this is the event bus shared for the app quiz screen
class EventBusShared {
    private val _eventFlow = MutableSharedFlow<AppEvent>()

    fun subscribe(scope: CoroutineScope, block: suspend (AppEvent) -> Unit) = _eventFlow.onEach(block).launchIn(scope)
    suspend fun emit(appEvent: AppEvent) = _eventFlow.emit(appEvent)
}