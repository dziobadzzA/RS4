package com.example.myfirstapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MentorDatabase::class], version = 1, exportSchema = false)
abstract  class DatabaseSql : RoomDatabase() {

    abstract val mentorDatabaseDao: MentorDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseSql? = null

        fun getInstance(context: Context): DatabaseSql {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseSql::class.java,
                        "Mentor"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}