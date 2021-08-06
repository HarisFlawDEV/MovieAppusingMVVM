package com.example.movieappusingmvvm.data.api

import com.example.movieappusingmvvm.data.vo.MovieResponse
import com.example.movieappusingmvvm.data.vo.PopularMoviesDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    //links for movies
    //    https://api.themoviedb.org/3/movie/497698?api_key=3aeed2272c8c1ad30fb70612d16aed68
    //    https://api.themoviedb.org/3/movie/popular?api_key=3aeed2272c8c1ad30fb70612d16aed68
    //    https://api.themoviedb.org/3/movie/upcoming?api_key=3aeed2272c8c1ad30fb70612d16aed68&language=en-US&page=1

    //links for tv shows
    //    https://api.themoviedb.org/3/tv/popular?api_key=3aeed2272c8c1ad30fb70612d16aed68
    //    https://api.themoviedb.org/3/tv/84958?api_key=3aeed2272c8c1ad30fb70612d16aed68

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id : Int) : Single<PopularMoviesDetails>



}