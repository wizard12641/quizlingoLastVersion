package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.usecase.LoginDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the login view model
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginDataUseCase: LoginDataUseCase
)  : ViewModel() {


    private val _loginLiveData: MutableLiveData<UserData?> = MutableLiveData()
    val loginLiveData: LiveData<UserData?> get() = _loginLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loginCheck(email: String, password: String){
        viewModelScope.launch {
            val result = loginDataUseCase.loginData(email, password)
            when(result){
                is Resource.Success -> {
                    _loginLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}