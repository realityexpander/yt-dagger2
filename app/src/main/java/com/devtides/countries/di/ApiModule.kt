package com.devtides.countries.di

import com.devtides.countries.model.CountriesApi
import com.devtides.countries.model.CountriesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

// sets the module to be supplied in the @Component
@Module
class ApiModule {
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com"
    }

    @Provides
    fun provideCountriesApi(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    @Provides
    fun provideCountriesService(): CountriesService {
        return CountriesService()
    }

    @Provides
    fun aSpecialString() = "A Special String"

    @Provides
    @Named("anotherString")
    fun anotherString() = "Another String"

    @Provides
    @Named("bString")
    fun aString() = theString() //"From String Module"
}

@Module
class StringsModule {
    @Provides
    @Named("aString")
    fun aString() = theString() //"From String Module"
//    fun aString() = "From String Module"
}

fun theString() = "the String"