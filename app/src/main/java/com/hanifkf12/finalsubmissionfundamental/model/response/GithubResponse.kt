package com.hanifkf12.finalsubmissionfundamental.model.response


import com.google.gson.annotations.SerializedName

data class GithubResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("total_count")
    val totalCount: Int?
)