package com.edu.quizlingo.repository

import com.edu.quizlingo.model.QuestionAndAnswer
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.model.response.GeneralResponse
import com.edu.quizlingo.room.QuizLingoAppDao
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

//this is the quizlingo repository
@ActivityScoped
class QuizlingoRepository @Inject constructor(private val api: QuizLingoAppDao){

    //register user
    suspend fun registerUser(userData: UserData) : Resource<GeneralResponse> {
        val response = try {
            api.registerUser(userData)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "User registered successfully"))
    }

    //login check user
    suspend fun loginCheckUser(email: String, password: String) : Resource<UserData> {
        val response = try {
            api.loginCheckUser(email, password)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    //get category
    suspend fun getCategory() : Resource<List<CategoryData>> {
        val response = try {
            api.getCategory()
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    //register quiz
    suspend fun registerQuiz(questionData: QuestionData) : Resource<GeneralResponse> {
        val response = try {
            api.registerQuiz(questionData)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "Quiz registered successfully"))
    }

    //get quiz list
    suspend fun quizList(categoryId: Int) : Resource<List<QuestionData>> {
        val response = try {
            api.getQuizList(categoryId)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    //save answer
    suspend fun saveAnswer(saveAnswer: SaveAnswer) : Resource<GeneralResponse> {
        val response = try {
            api.saveAnswer(saveAnswer)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "Answer saved successfully"))
    }

    //get answer list by id and category id
    suspend fun getAnswer(categoryId: Int) : Resource<List<QuestionAndAnswer>?> {
        val response = try {
            QuizLingoSingleton.user?.uid?.let { api.getAnswerList(categoryId, it) }
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    //user update by id
    suspend fun userUpdate(userData: UserData) : Resource<GeneralResponse?> {
        val response = try {
            api.userUpdate(userData.uid, userData.userName, userData.userSurname, userData.userEmail, userData.userPassword, userData.userAdmin)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "User updated successfully"))
    }

    //save category data
    suspend fun saveCategory(categoryData: CategoryData) : Resource<GeneralResponse?> {
        val response = try {
             api.saveCategory(categoryData)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "Category saved successfully"))
    }

    //get users app
    suspend fun getUsers() : Resource<List<UserData>?> {
        val response = try {
            api.getUsers()
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    //check quiz completed by id
    suspend fun checkQuizCompleted(categoryId: Int) : Resource<GeneralResponse?> {
        val response = try {
           api.checkQuizCompleted(categoryId)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        if (response >= 1){
            return Resource.Success(GeneralResponse(status = true, message = "Quiz completed"))
        }else{
            return Resource.Success(GeneralResponse(status = false, message = "Quiz Not completed"))
        }
    }

    //remove all answers
    suspend fun removeAllAnswers() : Resource<GeneralResponse?> {
        val response = try {
            api.removeAllAnswers()
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(GeneralResponse(status = true, message = "All answers removed"))
    }

}