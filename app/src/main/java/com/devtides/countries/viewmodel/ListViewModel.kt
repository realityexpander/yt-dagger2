package com.devtides.countries.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.countries.di.DaggerApiComponent
import com.devtides.countries.model.CountriesService
import com.devtides.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "ListViewModel"

class ListViewModel: ViewModel() {

    @Inject
    lateinit var countriesService: CountriesService

    @Inject
    lateinit var aSpecialString: String

    @Inject
    @Named("anotherString")
    lateinit var anotherString: String

    init {
        // from ApiComponent.kt, injects the countriesService
        //   looks for the @Inject annotation
        DaggerApiComponent.create().inject(this)

        Log.d(TAG, "a String: $aSpecialString")
        Log.d(TAG, "another String: $anotherString")
    }

    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}