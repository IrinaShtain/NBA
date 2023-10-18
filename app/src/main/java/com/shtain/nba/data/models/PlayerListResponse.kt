package com.shtain.nba.data.models

import com.google.gson.annotations.SerializedName

data class PlayerListResponse(
    val data: List<Player>,
    val meta: MetaData
)

data class MetaData(
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("next_page")
    val nextPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_count")
    val totalCount: Long

)
