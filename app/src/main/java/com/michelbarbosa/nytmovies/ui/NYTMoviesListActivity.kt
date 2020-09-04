package com.michelbarbosa.nytmovies.ui

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.enums.ErrorType
import com.michelbarbosa.nytmovies.presenter.NYTMoviesContract
import com.michelbarbosa.nytmovies.presenter.NYTMoviesPresenter
import com.michelbarbosa.nytmovies.util.Util
import michel566.androidmodules.lightdialog.DialogType
import michel566.androidmodules.lightdialog.LightDialog


class NYTMoviesListActivity : ListActivity(), NYTMoviesContract.ShowMoviesView,
    ItemMovieClickListener {

    private var adapter: NYTMoviesAdapter? = null
    private val presenter: NYTMoviesContract.NYTMoviesPresenter = NYTMoviesPresenter(this)
    lateinit var rvMovies: RecyclerView
    var context: Context = this@NYTMoviesListActivity
    var page: Int = 1

    companion object {
        const val REF_MOVIE = "reference_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutContent(R.layout.nytm_list_activity)
        setToolbar()
        setToolbarTitle(R.string.app_name)

        adapterConfig()
        if (Util.isFirstInstall(context)) {
            presenter.getAllMovies(this, "", 0)
        }
        showMovies()
    }

    private fun adapterConfig() {
        rvMovies = findViewById(R.id.rv_nytMovieList)
        adapter = NYTMoviesAdapter(this)
        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvMovies.layoutManager = linearLayoutManager
        rvMovies.adapter = adapter

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    loadWhileScrollPage()
                }
            }
        })
    }

    private fun loadWhileScrollPage() {
        presenter.getAllMovies(context, "", page)
        showMovies()
        page++
    }

    override fun onClickMovie(movie: Movie) {
        advanceToMovieDetails(context, REF_MOVIE, movie)
    }

    override fun loadAllMovies(movies: List<Movie>) {
        movieViewModel.insertMovieList(context, adapter?.getMovieList(), movies)
    }

    override fun showError(errorType: ErrorType, dialogType: DialogType) {
        when (errorType) {
            ErrorType.NETWORK, ErrorType.TIMEOUT -> {
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
            ErrorType.RESPONSE -> showErrorDialog(
                resources.getString(R.string.msg_ErroGenerico),
                dialogType
            )
            else -> showErrorDialog(resources.getString(R.string.msg_ErroGenerico), dialogType)
        }
    }

    private fun showErrorDialog(message: String, dialogType: DialogType) {
        val dialog = LightDialog(this, message, dialogType, true)
        dialog.show()
    }

    private fun showMovies() {
        movieViewModel.getAllMovies(context)!!.observe(
            this,
            Observer {
                adapter!!.setMovieList(it)
            }
        )

        movieViewModel.getResult(context)!!.observe(
            this,
            Observer {
                adapter!!.setMovieList(it)
            }
        )
    }

    override fun setAdapterFilter(newText: String?) {
        super.setAdapterFilter(newText)
        adapter?.filter?.filter(newText)
    }

    override fun findForQuery(query: String) {
        super.findForQuery(query)
        presenter.getAllMovies(this, query, 0)
        scrollToPositionList(0)
    }

    fun scrollToPositionList(pos: Int) {
        rvMovies.scrollToPosition(pos)
    }

}