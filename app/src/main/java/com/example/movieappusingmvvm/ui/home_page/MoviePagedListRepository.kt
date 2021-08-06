package com.example.movieappusingmvvm.ui.home_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import com.example.movieappusingmvvm.data.api.POST_PER_PAGE
import com.example.movieappusingmvvm.data.api.TheMovieDBInterface
import com.example.movieappusingmvvm.data.respository.MovieDataSource
import com.example.movieappusingmvvm.data.respository.MovieDataSourceFactory
import com.example.movieappusingmvvm.data.respository.NetworkState
import com.example.movieappusingmvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers

class MoviePagedListRepository (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}