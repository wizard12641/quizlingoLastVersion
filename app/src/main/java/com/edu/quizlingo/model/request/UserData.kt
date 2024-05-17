package com.edu.quizlingo.model.request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @ColumnInfo(name = "userName")
    var userName: String? = null,
    @ColumnInfo(name = "userSurname")
    var userSurname: String? = null,
    @ColumnInfo(name = "userEmail")
    var userEmail: String? = null,
    @ColumnInfo(name = "userPassword")
    var userPassword: String? = null,
    @ColumnInfo(name = "userAdmin")
    var userAdmin: Int? = 0
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
