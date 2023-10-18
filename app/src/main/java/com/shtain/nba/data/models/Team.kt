package com.shtain.nba.data.models

import com.google.gson.annotations.SerializedName

data class Team(
    val id: Long,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerializedName("full_name")
    val fullName: String,
    val name: String,
)