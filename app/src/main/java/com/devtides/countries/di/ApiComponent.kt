package com.devtides.countries.di

import androidx.lifecycle.ViewModel
import com.devtides.countries.model.CountriesService
import com.devtides.countries.view.MainActivity
import com.devtides.countries.viewmodel.ListViewModel
import dagger.Component

// Gets the objects from the modules defined here:
@Component(modules = [ApiModule::class])
interface ApiComponent {
    // Gets ANYTHING NEEDED from the modules= and injects it
    //  to the specified class.

    // Injects objects into the @Inject site(s) in the CountriesService class
    fun inject(countriesService: CountriesService) // injects CountriesApi

    // Injects objects into the @Inject site(s) in the ListViewModel class
    fun inject(listViewModel: ListViewModel) // injects CountriesService

//    // CANT DO THIS!!! 2 modules cant inject a given class
//    fun inject(mainActivity: MainActivity)
}

// Grabs objects from the modules
@Component(modules = [StringsModule::class, ApiModule::class])
interface StringsComponent {
    // Gets ANYTHING NEEDED from the modules= and injects it
    //  to the specified class.

    // Injects objects into the @Inject site(s) in the MainActivity class
    fun inject(mainActivity: MainActivity)
}