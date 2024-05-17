package com.edu.quizlingo.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.request.SaveAnswer

data class QuestionAndAnswer(
    val quizTitle: String,
    val quizQuestion: String,
    val quizOptions: String,
    val quizTrueOptions: String,
    val categoryId: Int,
    val questionId: Int,
    val answerText: String,
    val userId: Int,
    val quizId: Int,
    val answerId: Int
)