package com.michelbarbosa.nytmovies.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.data.dao.movie.MovieViewModel
import com.michelbarbosa.nytmovies.ui.NYTMoviesListActivity

open class BaseActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    protected lateinit var movieViewModel: MovieViewModel
    protected var rootLayout: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar!!.elevation = 4f
        }
        enableDisplayShow(false)
    }

    protected fun setToolbarTitle(idResourceTitle: Int) {
        if (idResourceTitle != 0) {
            val titleToolbar = findViewById<TextView>(R.id.titleToolbar)
            val title = resources.getString(idResourceTitle)
            titleToolbar.text = title
            if (title.length > 23) {
                titleToolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            } else if (title.length > 19) {
                titleToolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            }
        }
    }

    fun enableDisplayShow(isShow: Boolean) {
        if (toolbar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(isShow)
            supportActionBar!!.setDisplayShowTitleEnabled(isShow)
        }
    }

    fun setLayoutContent(resourceContentView: Int) {
        val dinamicContent = findViewById<RelativeLayout>(R.id.content)
        rootLayout = dinamicContent
        val view =
            layoutInflater.inflate(resourceContentView, dinamicContent, false)
        dinamicContent.addView(view)
    }

    protected fun advanceToMovieDetails(
        context: Context,
        ref: String?,
        movie: Movie?
    ) {
        val it = Intent(context, NYTMovieDetailActivity::class.java)
        it.putExtra(ref, movie)
        it.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        context.startActivity(it)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    protected fun backToMovieList(context: Context) {
        val it = Intent(context, NYTMoviesListActivity::class.java)
        it.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(it)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}