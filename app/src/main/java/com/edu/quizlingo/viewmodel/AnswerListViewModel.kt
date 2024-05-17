package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.QuestionAndAnswer
import com.edu.quizlingo.usecase.AnswerListDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the answer list view model
@HiltViewModel
class AnswerListViewModel @Inject constructor(
    private val answerDataUseCase: AnswerListDataUseCase
)  : ViewModel() {


    private val _answerLiveData: MutableLiveData<List<QuestionAndAnswer>?> = MutableLiveData()
    val answerLiveData: LiveData<List<QuestionAndAnswer>?> get() = _answerLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun getAnswerList(categoryId: Int){
        viewModelScope.launch {
            val result = answerDataUseCase.getAnswer(categoryId)
            when(result){
                is Resource.Success -> {
                    _answerLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}