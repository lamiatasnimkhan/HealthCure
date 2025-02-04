package com.example.niramoy.nurse

data class Request(
    val name: String,
    val details: String,
    val duration: String,
    val period: String,
    val location: String,
    val salary: String,
    val profileImageResId: Int
)
