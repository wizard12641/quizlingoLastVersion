package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.usecase.SaveAnswerDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the save answer view model
@HiltViewModel
class SaveAnswerViewModel @Inject constructor(
    private val saveAnswerDataUseCase: SaveAnswerDataUseCase
)  : ViewModel() {


    private val _saveAnswerLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val saveAnswerLiveData: LiveData<GeneralResponse?> get() = _saveAnswerLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    //this function is used to save the answer
    fun saveAnswer(saveAnswer: SaveAnswer){
        viewModelScope.launch {
            val result = saveAnswerDataUseCase.saveAnswer(saveAnswer)
            when(result){
                is Resource.Success -> {
                    _saveAnswerLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}