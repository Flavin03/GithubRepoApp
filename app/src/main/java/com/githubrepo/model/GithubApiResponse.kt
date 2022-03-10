package com.githubrepo.model

import com.githubrepo.data.GithubEntity
import com.google.gson.annotations.SerializedName

data class GithubApiResponse(
    @SerializedName("total_count")
    var totalCount: Long? = null,

    @SerializedName("items")
    var items: ArrayList<GithubEntity> = arrayListOf()
)
