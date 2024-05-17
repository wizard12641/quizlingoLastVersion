package com.edu.quizlingo.model.request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SaveAnswer(
    @ColumnInfo(name = "answerText")
    var answerText: String? = null,
    @ColumnInfo(name = "userId")
    var userId: Int? = null,
    @ColumnInfo(name = "quizId")
    var quizId: Int? = null
){
    @ColumnInfo(name = "answerId")
    @PrimaryKey(autoGenerate = true) var answerId: Int = 0
}
