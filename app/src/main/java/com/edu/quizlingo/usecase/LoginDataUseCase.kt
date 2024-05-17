package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the login data use case
class LoginDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun loginData(email: String, password: String) : Resource<UserData> = quizLingoRepository.loginCheckUser(email, password)
}
