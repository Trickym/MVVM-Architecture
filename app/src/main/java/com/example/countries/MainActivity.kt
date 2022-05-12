    package com.example.countries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.view.CountryListAdapter
import com.example.countries.viewmodel.ListViewModel

    class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private lateinit var errorText:TextView
    private lateinit var progress:ProgressBar
    private lateinit var countriesList : RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()
        countriesList = findViewById(R.id.countries_list)
        errorText = findViewById(R.id.list_error)
        progress = findViewById(R.id.loading_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

        private fun observeViewModel() {
            viewModel.countries.observe(this, Observer {
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            })
            viewModel.countryLoadError.observe(this, Observer {
                if(it) errorText.visibility=View.VISIBLE
                else errorText.visibility=View.GONE
            })
            viewModel.countryLoading.observe(this, Observer {
                if(it) {
                    progress.visibility = View.VISIBLE
                    errorText.visibility=View.GONE
                    countriesList.visibility = View.GONE
                }
                else progress.visibility=View.GONE
            })
        }

    }