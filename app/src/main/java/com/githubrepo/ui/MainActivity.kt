package com.githubrepo.ui

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
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var appStoreHomeViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private var isConnected : Boolean = true
    var dataList: ArrayList<GithubEntity> = ArrayList()
    private lateinit var recyclerviewAdapter: RecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
       // appStoreHomeViewModel = injectViewModel(viewModelFactory)
        appStoreHomeViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainModelHome = appStoreHomeViewModel
        /*binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            subscribeDataCallBack()
        }
        setRecyclerView(dataList)   //send empty list initially
        subscribeDataCallBack()*/
        isConnected = ConnectivityUtil.isConnected(this)
        if (!isConnected)
            Toast.makeText(this,"No internet connection!", Toast.LENGTH_SHORT).show()
        recyclerviewAdapter = RecyclerviewAdapter()
        binding.recyclerview.adapter = recyclerviewAdapter
        subscribeUI(recyclerviewAdapter)
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

   /* private fun subscribeDataCallBack() {
        binding.progress.visibility = View.VISIBLE
        appStoreHomeViewModel.getData()?.observe(this, Observer<ArrayList<GithubEntity>> {
            if (!it.isNullOrEmpty()) {
                dataList = it
                recyclerviewAdapter.setAppList(it)
                binding.swipeRefresh.isRefreshing = false
                binding.noData.visibility = View.GONE
            } else {
                binding.noData.visibility = View.VISIBLE
            }
            binding.progress.visibility = View.GONE
        })
    }

    private fun setRecyclerView(dataList: ArrayList<GithubEntity>) {
        recyclerviewAdapter = RecyclerviewAdapter()
        recyclerviewAdapter.onLayoutClickListener(object :
            RecyclerviewAdapter.RecyclerLayoutClickListener {
            override fun redirectToDetailScreen() {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(Constants.GITHUBENTITY, this@MainActivity.dataList[0])
                startActivity(intent)
            }
        })
        val categoryLinearLayoutManager = LinearLayoutManager(this)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerview.layoutManager = categoryLinearLayoutManager
        recyclerviewAdapter.setAppList(dataList)
        binding.recyclerview.adapter = recyclerviewAdapter
    }*/
}