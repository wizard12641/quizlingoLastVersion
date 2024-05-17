package com.edu.quizlingo.model.request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryData(
   @ColumnInfo(name = "categoryName")
   var categoryName: String
){
   @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
