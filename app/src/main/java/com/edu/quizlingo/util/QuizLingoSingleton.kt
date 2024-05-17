package com.edu.quizlingo.util

import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.UserData

//this is the quiz lingo singleton
object QuizLingoSingleton {
   var user: UserData? = null
   var categoryResponse: List<CategoryData>? = null
   var categoryId: Int = -1
}