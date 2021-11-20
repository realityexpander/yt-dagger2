package com.devtides.countries.model

import android.util.Log
import com.devtides.countries.di.ApiModule
import com.devtides.countries.di.DaggerApiComponent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

private const val TAG = "CountriesService"

class CountriesService {

    @Inject
    lateinit var api: CountriesApi
//    = Retrofit.Builder()
//        .baseUrl(ApiModule.BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//        .create(CountriesApi::class.java)

    @Inject
    lateinit var aSpecialString: String

    init {
        // 1. Looks for the @Inject annotation
        // 2. from ApiComponent, Injects anything from it
        DaggerApiComponent.create().inject(this)

        Log.d(TAG, "aSpecialString: $aSpecialString")
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}