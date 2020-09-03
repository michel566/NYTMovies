package com.michelbarbosa.nytmovies.ui

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.squareup.picasso.Picasso

class NYTMovieDetailActivity : BaseActivity() {
    private var tvTitle: TextView? = null
    private var tvMpaaRating: TextView? = null
    private var tvByLine: TextView? = null
    private var tvHeadLine: TextView? = null
    private var tvSummaryShort: TextView? = null
    private var tvLink: TextView? = null
    private var tvPublicationDate: TextView? = null
    private var ivDetail: ImageView? = null
    private var ivFavorite: ImageView? = null
    private var ivShare: ImageView? = null

    private val context: Context = this@NYTMovieDetailActivity
    private var movie: Movie? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nytm_detail_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setViews()
        data
    }

    private fun setViews() {
        tvTitle = findViewById(R.id.tv_nytm_detail_title)
        tvMpaaRating = findViewById(R.id.tv_nytm_detail_mpaaRating)
        tvByLine = findViewById(R.id.tv_nytm_detail_byLine)
        tvHeadLine = findViewById(R.id.tv_nytm_detail_headline)
        tvSummaryShort = findViewById(R.id.tv_nytm_detail_summaryShort)
        tvLink = findViewById(R.id.tv_nytm_detail_link)
        tvPublicationDate = findViewById(R.id.tv_nytm_detail_publicationDate)
        ivDetail = findViewById(R.id.iv_nytm_detail)
        ivFavorite = findViewById(R.id.iv_nytm_detail_favorite)
        ivShare = findViewById(R.id.iv_nytm_detail_share)
    }

    private val data: Unit
        private get() {
            movie =
                intent.getSerializableExtra(NYTMoviesListActivity.REF_MOVIE) as Movie
            if (movie != null) {
                isFavorite = movie!!.favorite
                setIconFavorite()
                tvTitle!!.text = movie!!.title
                tvMpaaRating!!.text = resources.getString(
                    R.string.tv_nytm_detail_mpaaRating,
                    movie!!.mpaaRating
                )
                tvByLine!!.text =
                    resources.getString(R.string.tv_nytm_detail_byLine, movie!!.byLine)
                tvHeadLine!!.text = movie!!.headLine
                tvSummaryShort!!.text = movie!!.summaryShort
                tvLink!!.text = resources.getString(
                    R.string.tv_nytm_detail_link,
                    movie!!.suggested_link_text
                )
                tvTitle!!.text = movie!!.title
                Picasso.get().load(movie!!.urlPicture).into(ivDetail)

                ivFavorite!!.setOnClickListener {
                    ivFavorite!!.isEnabled = false
                    setFavorite()
                    movieViewModel.updateSetFavorite(context, movie!!.Id!!, isFavorite)
                    ivFavorite!!.isEnabled = true
                }
            }
        }

    private fun setFavorite() {
        if (isFavorite) {
            isFavorite = false
            Toast.makeText(
                context,
                "${movie!!.title} is not your favorite movie",
                Toast.LENGTH_LONG
            ).show()
        } else {
            isFavorite = true
            Toast.makeText(context, "${movie!!.title} is your favorite movie", Toast.LENGTH_LONG)
                .show()

        }
        setIconFavorite()
    }

    private fun setIconFavorite() {
        if (isFavorite) {
            ivFavorite!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
        } else {
            ivFavorite!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
    }

    override fun onBackPressed() {
        backToMovieList(this)
    }
}