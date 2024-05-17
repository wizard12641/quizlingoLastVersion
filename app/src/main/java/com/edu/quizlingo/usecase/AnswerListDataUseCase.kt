package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.QuestionAndAnswer
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the answer list data use case
class AnswerListDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun getAnswer(categoryId: Int) : Resource<List<QuestionAndAnswer>?> = quizLingoRepository.getAnswer(categoryId)
}