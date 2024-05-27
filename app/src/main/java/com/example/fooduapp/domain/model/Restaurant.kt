package com.example.fooduapp.domain.model

import com.google.gson.Gson

data class Restaurant(
    var id: String = "",
    var name: String = "",
    var descrip: String = "",
    var img: String = "",
    var rating: String = "",
    var foods: List<String> = emptyList()
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(data: String): Restaurant = Gson().fromJson(data, Restaurant::class.java)
    }
}
