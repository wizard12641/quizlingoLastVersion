package com.edu.quizlingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.quizlingo.model.request.LoginData
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.usecase.EditProfileDataUseCase
import com.edu.quizlingo.usecase.LoginDataUseCase
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the profile view model
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editProfileDataUseCase: EditProfileDataUseCase,
    private val loginDataUseCase: LoginDataUseCase
)  : ViewModel() {


    private val _editProfileLiveData: MutableLiveData<GeneralResponse?> = MutableLiveData()
    val editProfileLiveData: LiveData<GeneralResponse?> get() = _editProfileLiveData

    private val _loginLiveData: MutableLiveData<UserData?> = MutableLiveData()
    val loginLiveData: LiveData<UserData?> get() = _loginLiveData

    private val _getUserLiveData: MutableLiveData<List<UserData>?> = MutableLiveData()
    val getUserLiveData: LiveData<List<UserData>?> get() = _getUserLiveData

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun editProfile(userData: UserData){
        viewModelScope.launch {
            val result = editProfileDataUseCase.editProfile(userData)
            when(result){
                is Resource.Success -> {
                    _editProfileLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }

    fun loginCheck(loginData: LoginData){
        viewModelScope.launch {
            val result = loginDataUseCase.loginData(loginData.userEmail.toString(), loginData.userPassword.toString())
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

    fun getUsers(){
        viewModelScope.launch {
            val result = editProfileDataUseCase.getUsers()
            when(result){
                is Resource.Success -> {
                    _getUserLiveData.value = result.data
                }
                is Resource.Error -> {
                    _errorMessage.value = result.message.toString()
                }
                else -> {}
            }
        }
    }
}