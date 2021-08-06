package com.example.movieappusingmvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.movieappusingmvvm.data.api.TheMovieDBInterface
import com.example.movieappusingmvvm.data.respository.MoviesDetailsNetworkDataSource
import com.example.movieappusingmvvm.data.respository.NetworkState
import com.example.movieappusingmvvm.data.vo.PopularMoviesDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRespository(private val apiService : TheMovieDBInterface) {
    lateinit var moviesDetailsNetworkDataSource: MoviesDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable , movieId : Int ) : LiveData<PopularMoviesDetails>{

        moviesDetailsNetworkDataSource = MoviesDetailsNetworkDataSource(apiService,compositeDisposable)
        moviesDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return  moviesDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState() : LiveData<NetworkState> {
        return moviesDetailsNetworkDataSource.networkState
    }

}