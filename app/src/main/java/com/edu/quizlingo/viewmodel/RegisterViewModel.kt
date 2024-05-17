package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.usecase.RegisterDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the register view model
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerDataUseCase: RegisterDataUseCase
)  : ViewModel() {

    private val _registerLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val registerLiveData: LiveData<GeneralResponse?> get() = _registerLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun registerUser(userData: UserData){
        viewModelScope.launch {
            val result = registerDataUseCase.registerUser(userData)
            when(result){
                is Resource.Success -> {
                    _registerLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}