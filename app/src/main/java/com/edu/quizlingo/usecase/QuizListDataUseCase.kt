package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the quiz list data use case
class QuizListDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun quizList(categoryId: Int) : Resource<List<QuestionData>> = quizLingoRepository.quizList(categoryId)
}