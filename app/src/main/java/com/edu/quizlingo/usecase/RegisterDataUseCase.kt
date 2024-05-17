package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the register data use case
class RegisterDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun registerUser(userData: UserData) : Resource<GeneralResponse> = quizLingoRepository.registerUser(userData)
}