package com.devtides.countries.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devtides.countries.R
import com.devtides.countries.di.DaggerApiComponent
import com.devtides.countries.di.DaggerStringsComponent
import com.devtides.countries.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

// From: https://www.youtube.com/watch?v=bV3jf8VpbSY
// https://github.com/CatalinStefan/yt-dagger2

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())

    @Inject
    @Named("aString")
    lateinit var aString: String

    @Inject
    @Named("anotherString")
    lateinit var anotherString: String;

    init {
        // DaggerApiComponent.create().inject(this) // CANT DO THIS. Only one Component can inject into a given Class
        DaggerStringsComponent.create().inject(this)

        Log.d(TAG, "aString: $aString")
        Log.d(TAG, "anotherString: $anotherString")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

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

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    list_error.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }
        })
    }
}
