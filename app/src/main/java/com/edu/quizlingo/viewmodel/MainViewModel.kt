package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.usecase.MainDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the main view model
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainDataUseCase: MainDataUseCase
)  : ViewModel() {


    private val _mainCategoryLiveData: MutableLiveData<List<CategoryData>?> = MutableLiveData()
    val mainCategoryLiveData: LiveData<List<CategoryData>?> get() = _mainCategoryLiveData

    private val _checkCompletedLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val checkCompletedLiveData: LiveData<GeneralResponse?> get() = _checkCompletedLiveData

    private val _removeAnswersLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val removeAnswersLiveData: LiveData<GeneralResponse?> get() = _removeAnswersLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun getCategory(){
        viewModelScope.launch {
            val result = mainDataUseCase.getCategory()
            when(result){
                is Resource.Success -> {
                    _mainCategoryLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }

    fun checkQuizCompleted(categoryId: Int){
        viewModelScope.launch {
            val result = mainDataUseCase.checkQuizCompleted(categoryId)
            when(result){
                is Resource.Success -> {
                    _checkCompletedLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }

    fun removeAllAnswers(){
        viewModelScope.launch {
            val result = mainDataUseCase.removeAllAnswers()
            when(result){
                is Resource.Success -> {
                    _removeAnswersLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}