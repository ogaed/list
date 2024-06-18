package com.example.test

data class Attraction(
    val id: Int,
    val email: String,
    val created_at: String,
    val updated_at: String,
    val first_name: String,
    val last_name: String,
    val role: Int,
    val location: String,
    val national_id: Int,
    val phone_number: String,
    val expertise: String,
    val languages: String,
    val experience: String,
    val bio: String,
    val preferences: String?,
    val interests: String?,
    val category: String,
    val average_rating: Double,
    val number_of_ratings: Int
)