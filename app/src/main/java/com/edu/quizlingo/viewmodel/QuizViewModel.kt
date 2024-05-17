package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.usecase.QuizListDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the quiz view model
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizListDataUseCase: QuizListDataUseCase
)  : ViewModel() {


    private val _quizLiveData: MutableLiveData<List<QuestionData>?> = MutableLiveData()
    val quizLiveData: LiveData<List<QuestionData>?> get() = _quizLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun quizList(categoryId: Int){
        viewModelScope.launch {
            val result = quizListDataUseCase.quizList(categoryId)
            when(result){
                is Resource.Success -> {
                    _quizLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}