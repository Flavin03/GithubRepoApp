package com.githubrepo.model

import com.githubrepo.data.GithubEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GithubApiResponse(
    @Expose
    @SerializedName("total_count")
    var totalCount: Long? = null,

    @Expose
    @SerializedName("items")
    var items: ArrayList<GithubEntity> = arrayListOf()
)
