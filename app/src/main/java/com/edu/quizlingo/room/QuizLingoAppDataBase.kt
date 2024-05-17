package com.edu.quizlingo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.LoginData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.model.request.UserData

//Create DB
@Database(entities = [UserData::class, LoginData::class, QuestionData::class, SaveAnswer::class, CategoryData::class], version = 1)
abstract class QuizLingoAppDataBase : RoomDatabase() {
    abstract fun quizLingoAppDAO() : QuizLingoAppDao
}