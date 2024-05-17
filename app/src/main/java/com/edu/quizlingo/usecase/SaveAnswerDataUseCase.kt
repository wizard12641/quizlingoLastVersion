package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the save answer data use case
class SaveAnswerDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun saveAnswer(saveAnswer: SaveAnswer) : Resource<GeneralResponse> = quizLingoRepository.saveAnswer(saveAnswer)
}