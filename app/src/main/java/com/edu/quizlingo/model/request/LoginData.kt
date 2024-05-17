package com.edu.quizlingo.model.request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginData(
    @ColumnInfo(name = "userEmail")
    var userEmail: String? = null,
    @ColumnInfo(name = "userPassword")
    var userPassword: String? = null
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
