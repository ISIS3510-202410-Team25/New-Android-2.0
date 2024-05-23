package com.example.fooduapp.domain.model

import com.google.gson.Gson

data class Food (
    var id: String = "",
    var name: String = "",
    var rating: Double = 0.0,
    var price: Double = 0.0,
    var img: String = ""
) {

    fun toJson(): String = Gson().toJson(
        Food(
            id = id,
            name = name,
            rating = rating,
            price = price,
            img = img
        )
    )

    fun fromJson(data: String): Food = Gson().fromJson(data, Food::class.java)

}