package com.edu.quizlingo.model.request

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class QuestionData(
    @ColumnInfo(name = "quizTitle")
    var quizTitle: String? = null,
    @ColumnInfo(name = "quizQuestion")
    var quizQuestion: String? = null,
    @ColumnInfo(name = "quizOptions")
    var quizOptions: String? = null,
    @ColumnInfo(name = "quizTrueOptions")
    var quizTrueOptions: String? = null,
    @ColumnInfo(name = "categoryId")
    var categoryId: Int? = null
): Parcelable {
    @ColumnInfo(name = "questionId")
    @PrimaryKey(autoGenerate = true) var questionId: Int = 0
}
