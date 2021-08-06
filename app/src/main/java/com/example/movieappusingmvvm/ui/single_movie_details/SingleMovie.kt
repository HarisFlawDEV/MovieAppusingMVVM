package com.example.movieappusingmvvm.ui.single_movie_details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movieappusingmvvm.R
import com.example.movieappusingmvvm.data.api.POSTER_BASE_URL
import com.example.movieappusingmvvm.data.api.TheMovieDBClient
import com.example.movieappusingmvvm.data.api.TheMovieDBInterface
import com.example.movieappusingmvvm.data.respository.NetworkState
import com.example.movieappusingmvvm.data.vo.PopularMoviesDetails
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRespository: MovieDetailsRespository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieDetailsRespository = MovieDetailsRespository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
            progressBar.visibility = if ( it == NetworkState.LOADING) View.VISIBLE else View.GONE
            val txt_error = findViewById<TextView>(R.id.txt_error)
            txt_error.visibility = if ( it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    @SuppressLint("SetTextI18n")
    fun bindUI(it: PopularMoviesDetails) {
        val movie_title =findViewById<TextView>( R.id.movie_title)
        val movie_tagline =findViewById<TextView>( R.id.movie_tagline)
        val movie_release_date =findViewById<TextView>( R.id.movie_release_date)
        val movie_rating =findViewById<TextView>( R.id.movie_rating)
        val movie_runtime =findViewById<TextView>( R.id.movie_runtime)
        val movie_overview =findViewById<TextView>( R.id.movie_overview)
        val movie_poster =findViewById<ImageView>( R.id.movie_poster)


        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text  = it.runtime.toString() + " Minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        formatCurrency.format(it.budget)
        formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath

        Glide.with(this)
            .load(moviePosterURL)
            .into(movie_poster)


    }

    private fun getViewModel(movieId : Int):SingleMovieViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRespository,movieId) as T
            }
        }
        )[SingleMovieViewModel::class.java]

    }
}