package com.michelbarbosa.nytmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.enums.ErrorType
import com.michelbarbosa.nytmovies.model.Movie
import com.michelbarbosa.nytmovies.presenter.NYTMoviesContract
import com.michelbarbosa.nytmovies.presenter.NYTMoviesPresenter
import michel566.androidmodules.lightdialog.DialogType
import michel566.androidmodules.lightdialog.LightDialog

class NYTMoviesActivity : AppCompatActivity(), NYTMoviesContract.ShowMoviesView, ItemMovieClickListener{

    private var adapter: NYTMoviesAdapter? = null
    private val presenter: NYTMoviesContract.NYTMoviesPresenter = NYTMoviesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nytmovies_activity)

        adapterConfig()

        //teste de chamada de api
        presenter.getAllMovies(this, "", 1)
    }

    private fun adapterConfig(){
        val rvMovies: RecyclerView = findViewById(R.id.rv_nytMovieList)
        adapter = NYTMoviesAdapter(this)
        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvMovies.layoutManager = linearLayoutManager
        rvMovies.adapter = adapter
    }

    override fun onClickMovie(movie: Movie) {
        Toast.makeText(this, movie.dateUpdated, Toast.LENGTH_SHORT).show()
    }

    override fun showMovies(movies: List<Movie>) {
        adapter?.setMovieList(movies)
    }

    override fun showError(errorType: ErrorType, dialogType: DialogType) {
        when (errorType) {
            ErrorType.NETWORK, ErrorType.TIMEOUT ->{
                showErrorDialog(resources.getString(R.string.msg_ErroTimeout), dialogType)
            }
            ErrorType.CURRENT_OBJ_NULL -> {
                val msg: String = (resources.getString(R.string.msg_ErroGenerico)
                        + resources.getString(R.string.msg_ErroGenerico))
                showErrorDialog(msg, dialogType)
            }
            ErrorType.JSON_OBJ_RETURN -> showErrorDialog(
                resources.getString(R.string.msg_ErroOBJJson),
                dialogType
            )
            ErrorType.RESPONSE -> showErrorDialog(resources.getString(R.string.msg_ErroGenerico), dialogType)
            else -> showErrorDialog(resources.getString(R.string.msg_ErroGenerico), dialogType)
        }
    }

    private fun showErrorDialog(message: String, dialogType: DialogType){
        val dialog = LightDialog(this, message, dialogType, true)
        dialog.show()
    }

}