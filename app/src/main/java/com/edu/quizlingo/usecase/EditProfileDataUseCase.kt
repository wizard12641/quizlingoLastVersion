package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the edit profile data use case
class EditProfileDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun editProfile(userData: UserData) : Resource<GeneralResponse?> = quizLingoRepository.userUpdate(userData)
    suspend fun getUsers() : Resource<List<UserData>?> = quizLingoRepository.getUsers()
}