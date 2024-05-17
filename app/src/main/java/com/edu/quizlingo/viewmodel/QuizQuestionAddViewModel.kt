package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.usecase.MainDataUseCase
import com.edu.quizlingo.usecase.RegisterQuizDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the quiz question add view model
@HiltViewModel
class QuizQuestionAddViewModel @Inject constructor(
    private val registerQuizDataUseCase: RegisterQuizDataUseCase,
    private val mainDataUseCase: MainDataUseCase
)  : ViewModel() {


    var selectedCategoryId: Int = -1

    private val _registerQuizLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val registerQuizLiveData: LiveData<GeneralResponse?> get() = _registerQuizLiveData

    private val _saveCategoryLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val saveCategoryLiveData: LiveData<GeneralResponse?> get() = _saveCategoryLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    //this function is used to register quiz
    fun registerQuiz(questionData: QuestionData){
        viewModelScope.launch {
            val result = registerQuizDataUseCase.registerQuiz(questionData)
            when(result){
                is Resource.Success -> {
                    _registerQuizLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }

    //this function is used to save category
    fun saveCategory(categoryData: CategoryData){
        viewModelScope.launch {
            val result = registerQuizDataUseCase.saveCategory(categoryData)
            when(result){
                is Resource.Success -> {
                    _saveCategoryLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }

    private val _mainCategoryLiveData: MutableLiveData<List<CategoryData>?> = MutableLiveData()
    val mainCategoryLiveData: LiveData<List<CategoryData>?> get() = _mainCategoryLiveData

    //this function is used to get category
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
}