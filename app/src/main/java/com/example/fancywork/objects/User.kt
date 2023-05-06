package com.example.fancywork.objects

data class User(
    val email: String?,
    val name: String?,
    val password: String?,
    val phone: String?,
    val url: String?,
){
    constructor(): this("","","","", "")
}
