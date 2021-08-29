package com.example.myfirstapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MentorDatabaseDao {

         @Insert
         suspend fun insert(mentor: MentorDatabase)

         @Query("SELECT * from Mentor WHERE Id = :key ORDER BY Id")
         suspend fun get(key: Long): MentorDatabase

         @Query("DELETE FROM Mentor WHERE Id = :key")
         suspend fun clearMentor(key: Long)

         @Query("DELETE FROM Mentor")
         suspend fun clearAll()

         @Query("UPDATE Mentor SET name = :name, firstname = :firstname, phone = :phone WHERE Id = :key")
         suspend fun update(name: String, firstname:String, phone:String, key: Long)

         @Query("SELECT * from Mentor ORDER BY phone")
         suspend fun getAllPhone(): List<MentorDatabase>

         @Query("SELECT * from Mentor ORDER BY firstname")
         suspend fun getAllFirstName(): List<MentorDatabase>

         @Query("SELECT * from Mentor ORDER BY name")
         suspend fun getAllName(): List<MentorDatabase>

         @Query("SELECT MAX(Id) FROM Mentor")
         suspend fun getLastIndex(): Long
}