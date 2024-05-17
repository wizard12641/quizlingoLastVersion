package com.edu.quizlingo.usecase

import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.util.Resource
import javax.inject.Inject

//this is the main data use case
class MainDataUseCase @Inject constructor(private val quizLingoRepository: QuizlingoRepository) {
    suspend fun getCategory() : Resource<List<CategoryData>> = quizLingoRepository.getCategory()
    suspend fun checkQuizCompleted(categoryId: Int) : Resource<GeneralResponse?> = quizLingoRepository.checkQuizCompleted(categoryId)
    suspend fun removeAllAnswers() : Resource<GeneralResponse?> = quizLingoRepository.removeAllAnswers()
}