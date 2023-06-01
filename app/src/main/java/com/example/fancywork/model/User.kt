package com.example.fancywork.model

data class User(
    val email: String?,
    val name: String?,
    val lastname: String?,
    val city: String?,
    val country: String?,
    val active: Boolean?,
    val state: String?,
    val password: String?,
    val phone: String?,
    val url: String?,
){
    constructor(): this("","","", "","", false, "", "", "", "")
}
