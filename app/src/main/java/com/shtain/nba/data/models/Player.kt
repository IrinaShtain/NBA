package com.shtain.nba.data.models

import com.google.gson.annotations.SerializedName


data class Player(
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("height_feet")
    val heightFeet: Long,
    @SerializedName("height_inches")
    val heightInches: Long,
    @SerializedName("weight_pounds")
    val weightPounds: Long,
    val position: String,
    val team: Team
){
    val fullName :String
        get()  = "$firstName $lastName"
}