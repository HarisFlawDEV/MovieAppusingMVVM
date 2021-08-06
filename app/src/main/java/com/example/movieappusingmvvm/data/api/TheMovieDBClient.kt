package com.example.movieappusingmvvm.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val API_KEY = "3aeed2272c8c1ad30fb70612d16aed68"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val  POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"


const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20



//links for movies
//    https://api.themoviedb.org/3/movie/497698?api_key=3aeed2272c8c1ad30fb70612d16aed68
//    https://api.themoviedb.org/3/movie/popular?api_key=3aeed2272c8c1ad30fb70612d16aed68
//    https://api.themoviedb.org/3/movie/upcoming?api_key=3aeed2272c8c1ad30fb70612d16aed68&language=en-US&page=1

//links for tv shows
//    https://api.themoviedb.org/3/tv/popular?api_key=3aeed2272c8c1ad30fb70612d16aed68
//    https://api.themoviedb.org/3/tv/84958?api_key=3aeed2272c8c1ad30fb70612d16aed68
//image poster full link
//    https://image.tmdb.org/t/p/w342/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg

object TheMovieDBClient {
    
    fun getClient() : TheMovieDBInterface {
        
        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)

    }
}