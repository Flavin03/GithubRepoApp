package com.githubrepo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.githubrepo.R
import com.githubrepo.adapters.RecyclerviewAdapter
import com.githubrepo.data.GithubEntity
import com.githubrepo.databinding.ActivityMainBinding
import com.githubrepo.network.NetworkState
import com.githubrepo.utils.ConnectivityUtil
import com.githubrepo.utils.Constants
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var appStoreHomeViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private var isConnected : Boolean = true
    private lateinit var recyclerviewAdapter: RecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appStoreHomeViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainModelHome = appStoreHomeViewModel
        isConnected = ConnectivityUtil.isConnected(this)
        if (!isConnected)
            Toast.makeText(this,"No internet connection!", Toast.LENGTH_SHORT).show()
        recyclerviewAdapter = RecyclerviewAdapter()
        binding.recyclerview.adapter = recyclerviewAdapter
        subscribeUI(recyclerviewAdapter)
        recyclerviewAdapter.setClickListener(object: RecyclerviewAdapter.ClickListener{
            override fun goToDetailScreen(githubEntity: GithubEntity) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(Constants.GITHUBENTITY, githubEntity)
                startActivity(intent)
            }
        })
    }

    private fun subscribeUI(adapter: RecyclerviewAdapter) {
        val data = appStoreHomeViewModel.getRepoList(isConnected)
        data?.networkState?.observe(this, Observer {
            when(it) {
                is NetworkState.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is NetworkState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    // Handle fail state
                }
                is NetworkState.LOADED -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })
        data?.pagedList?.observe(this, Observer {
            adapter.submitList(it)
        })
    }

}