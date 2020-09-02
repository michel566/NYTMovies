package com.michelbarbosa.nytmovies.network

import com.michelbarbosa.nytmovies.network.api.NYTMoviesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private var INSTANCE: NYTMoviesService? = null
    private val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"

    val instance: NYTMoviesService?
        get() {
            if (INSTANCE == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                INSTANCE = retrofit.create<NYTMoviesService>(NYTMoviesService::class.java)
            }

            return INSTANCE

        }

}