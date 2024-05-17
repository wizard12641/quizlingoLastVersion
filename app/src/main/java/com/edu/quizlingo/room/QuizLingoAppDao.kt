package com.edu.quizlingo.room

import androidx.room.*
import com.edu.quizlingo.model.QuestionAndAnswer
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.model.request.UserData

@Dao
interface QuizLingoAppDao {
    
    //insert user
    @Insert
    suspend fun registerUser(user: UserData)

    //check user
    @Query("SELECT * FROM UserData WHERE userEmail = :email AND userPassword = :password")
    suspend fun loginCheckUser(email: String, password: String): UserData

    //save category to db
    @Insert
    suspend fun saveCategory(category: CategoryData)

    //get category from db
    @Query("SELECT * FROM CategoryData")
    suspend fun getCategory(): List<CategoryData>

    //register quiz to db
    @Insert
    suspend fun registerQuiz(quiz: QuestionData)

    //get quiz list from db
    @Query("SELECT * FROM QuestionData WHERE categoryId = :categoryId")
    suspend fun getQuizList(categoryId: Int): List<QuestionData>

    //save answer to db
    @Insert
    suspend fun saveAnswer(answer: SaveAnswer)

    //check quiz completed
    @Query("SELECT COUNT(*) as 'count' FROM SaveAnswer WHERE quizId IN (SELECT quizId FROM QuestionData WHERE categoryId =:categoryId)")
    suspend fun checkQuizCompleted(categoryId: Int): Int

    //remove all answers
    @Query("DELETE FROM SaveAnswer")
    suspend fun removeAllAnswers()

    //get answer list
    @Transaction
    @Query("SELECT QuestionData.*, SaveAnswer.* FROM QuestionData INNER JOIN SaveAnswer ON QuestionData.questionId = SaveAnswer.quizId WHERE SaveAnswer.userId = :id AND QuestionData.categoryId = :categoryId")
    suspend fun getAnswerList(categoryId: Int, id: Int): List<QuestionAndAnswer>

    //get user by id
    @Query("UPDATE UserData SET userName = :userName, userSurname = :userSurname, userEmail = :userEmail, userPassword = :userPassword, userAdmin = :userAdmin WHERE uid = :uid")
    suspend fun userUpdate(uid: Int, userName: String?, userSurname: String?, userEmail: String?, userPassword: String?, userAdmin: Int?)

    //get user by id
    @Query("SELECT * FROM UserData")
    suspend fun getUsers(): List<UserData>
}