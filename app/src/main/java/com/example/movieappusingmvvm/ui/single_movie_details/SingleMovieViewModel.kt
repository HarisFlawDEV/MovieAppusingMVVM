package com.example.movieappusingmvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieappusingmvvm.data.respository.NetworkState
import com.example.movieappusingmvvm.data.vo.PopularMoviesDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRespository: MovieDetailsRespository, movieId : Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<PopularMoviesDetails> by lazy {
        movieDetailsRespository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieDetailsRespository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()

    }





}