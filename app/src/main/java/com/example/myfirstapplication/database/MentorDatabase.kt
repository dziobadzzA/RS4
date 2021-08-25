package com.example.myfirstapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mentor")
data class MentorDatabase(
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0L,

    @ColumnInfo(name = "Name")
    var name: String = "",

    @ColumnInfo(name = "FirstName")
    var firstname: String = "",

    @ColumnInfo(name = "Phone")
    var number: String = "")