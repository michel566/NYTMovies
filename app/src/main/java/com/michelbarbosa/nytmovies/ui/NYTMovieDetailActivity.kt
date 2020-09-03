package com.michelbarbosa.nytmovies.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
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
    private var movie: Movie? = null
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
            }
        }

    private fun changeFavorite(isActive: Boolean) {
        if (isActive) {
            ivFavorite!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
        } else {
            ivFavorite!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
    }

    override fun onBackPressed() {
        backToMovieList(this)
    }
}