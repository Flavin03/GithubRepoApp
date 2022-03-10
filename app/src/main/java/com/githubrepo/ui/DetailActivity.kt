package com.githubrepo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.githubrepo.R
import com.githubrepo.data.GithubEntity
import com.githubrepo.databinding.ActivityDetailBinding
import com.githubrepo.databinding.ActivityMainBinding
import com.githubrepo.utils.Constants

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        var githubEntity = intent.getParcelableExtra<GithubEntity>(Constants.GITHUBENTITY)
        Glide.with(this).load(githubEntity?.owner?.avatar_url).into(binding.detailImage)
        binding.repoName.text = githubEntity?.name
        binding.forksValue.text = githubEntity?.forks_count.toString()
        binding.starsValue.text = githubEntity?.stargazers_count.toString()
        binding.watchersValue.text = githubEntity?.watchers_count.toString()
    }
}