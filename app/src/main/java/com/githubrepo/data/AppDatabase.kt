package com.githubrepo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GithubEntity::class], version = 1, exportSchema = false)
@TypeConverters(*[TimestampConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
}