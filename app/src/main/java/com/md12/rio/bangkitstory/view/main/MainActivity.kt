package com.md12.rio.bangkitstory.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.md12.rio.bangkitstory.R
import com.md12.rio.bangkitstory.adapter.StoryAdapter
import com.md12.rio.bangkitstory.data.DataRepository
import com.md12.rio.bangkitstory.data.remote.api.ApiClient
import com.md12.rio.bangkitstory.data.remote.response.story.ListStory
import com.md12.rio.bangkitstory.databinding.ActivityMainBinding
import com.md12.rio.bangkitstory.utils.NetworkResult
import com.md12.rio.bangkitstory.utils.PrefsManager
import com.md12.rio.bangkitstory.utils.ViewModelFactory
import com.md12.rio.bangkitstory.view.addstory.AddStoryActivity
import com.md12.rio.bangkitstory.view.setting.SettingActivity
import com.md12.rio.bangkitstory.view.detailstory.DetailActivity

class MainActivity : AppCompatActivity(), StoryAdapter.OnItemClickAdapter {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var prefsManager: PrefsManager
    private lateinit var viewModel: MainViewModel
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        prefsManager = PrefsManager(this)
        storyAdapter = StoryAdapter(this, this)
        val dataRepository = DataRepository(ApiClient.getInstance())
        viewModel = ViewModelProvider(this, ViewModelFactory(dataRepository))[MainViewModel::class.java]
        fetchData(prefsManager.token)

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            fetchData(prefsManager.token)
        }

        binding.btnTry.setOnClickListener {
            setLoadingState(true)
            fetchData(prefsManager.token)
        }

        binding.fbAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.account_menu -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchData(auth: String) {
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter
        }
        viewModel.apply {
            setLoadingState(true)
            fetchListStory(auth)
            responseListStory.observe(this@MainActivity) {
                when(it) {
                    is NetworkResult.Success -> {
                        if(it.data?.listStory != null) {
                            storyAdapter.setData(it.data.listStory)
                            binding.btnTry.visibility = View.GONE
                        } else {
                            binding.btnTry.visibility = View.GONE
                            binding.rvStory.visibility = View.GONE
                            binding.tvNotFound.visibility = View.VISIBLE
                        }
                        binding.tvError.visibility = View.GONE
                        setLoadingState(false)
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is NetworkResult.Loading -> {
                        setLoadingState(true)
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is NetworkResult.Error -> {
                        setLoadingState(false)
                        binding.rvStory.visibility = View.GONE
                        binding.tvNotFound.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.btnTry.visibility = View.VISIBLE
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }

        }
    }

    override fun onItemClicked(listStory: ListStory, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ITEM, listStory)
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun setLoadingState(loading: Boolean) {
        when(loading) {
            true -> {
                binding.rvStory.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                binding.rvStory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}