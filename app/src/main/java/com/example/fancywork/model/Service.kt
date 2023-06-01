package com.example.fancywork.model

data class Service(
    val title: String?,
    val price: String?,
    val description: String?,
    val name: String?,
    val email: String?,
    val city: String?,
    val country: String?,
    val state: String?,
    val date: String?,
){
    constructor(): this("","","","", "","", "", "", "")
}

