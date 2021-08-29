package com.example.myfirstapplication.model

import com.example.myfirstapplication.database.MentorDatabase

interface AddMentor {
    fun addMentor(mentor: Mentor)
    fun updateMentor(mentor: Mentor)
    fun getUnique(mentor: Mentor):MentorDatabase?
}