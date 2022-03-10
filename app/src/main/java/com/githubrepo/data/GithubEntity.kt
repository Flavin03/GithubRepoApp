package com.githubrepo.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class GithubEntity(
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null,

    var page: Long? = null,

    var totalPages: Long? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("full_name")
    var full_name: String? = null,

    @SerializedName("owner")
    @Embedded
    var owner: Owner? = null,

    @SerializedName("html_url")
    var html_url: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("contributors_url")
    var contributors_url: String? = null,

    @TypeConverters(TimestampConverter::class)
    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("stargazers_count")
    var stargazers_count: Long? = null,

    @SerializedName("watchers_count")
    var watchers_count: Long? = null,

    @SerializedName("forks_count")
    var forks_count: Long? = null,

    @SerializedName("language")
    var language: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Owner::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(page)
        parcel.writeValue(totalPages)
        parcel.writeString(name)
        parcel.writeString(full_name)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(html_url)
        parcel.writeString(description)
        parcel.writeString(contributors_url)
        parcel.writeString(createdAt)
        parcel.writeValue(stargazers_count)
        parcel.writeValue(watchers_count)
        parcel.writeValue(forks_count)
        parcel.writeString(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GithubEntity> {
        override fun createFromParcel(parcel: Parcel): GithubEntity {
            return GithubEntity(parcel)
        }

        override fun newArray(size: Int): Array<GithubEntity?> {
            return arrayOfNulls(size)
        }
    }

}

@Entity
data class Owner(
    @SerializedName("login")
    var login: String? = null,

    @SerializedName("avatar_url")
    var avatar_url: String? = null,

    @SerializedName("url")
    var url: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(avatar_url)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }

}

