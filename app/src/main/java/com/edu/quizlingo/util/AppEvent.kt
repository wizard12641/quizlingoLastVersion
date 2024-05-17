package com.edu.quizlingo.util

//this is the app event class
sealed class AppEvent{
    data class NextQuestionId(val position: Int = 1) : AppEvent()
}
