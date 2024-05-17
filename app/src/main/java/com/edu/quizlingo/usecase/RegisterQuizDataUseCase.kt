package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the register quiz data use case
class RegisterQuizDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun registerQuiz(questionData: QuestionData) : Resource<GeneralResponse> = quizLingoRepository.registerQuiz(questionData)
    suspend fun saveCategory(categoryData: CategoryData) : Resource<GeneralResponse?> = quizLingoRepository.saveCategory(categoryData)
}