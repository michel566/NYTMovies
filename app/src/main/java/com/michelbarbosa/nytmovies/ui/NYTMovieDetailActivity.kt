package com.michelbarbosa.nytmovies.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.util.UiUtil


class NYTMovieDetailActivity : BaseActivity() {
    private var tvTitle: TextView? = null
    private var tvByLine: TextView? = null
    private var tvHeadLine: TextView? = null
    private var tvSummaryShort: TextView? = null
    private var tvLink: TextView? = null
    private var tvPublicationDate: TextView? = null
    private var tvUpdatedDate: TextView? = null
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
        tvByLine = findViewById(R.id.tv_nytm_detail_byLine)
        tvHeadLine = findViewById(R.id.tv_nytm_detail_headline)
        tvSummaryShort = findViewById(R.id.tv_nytm_detail_summaryShort)
        tvLink = findViewById(R.id.tv_nytm_detail_link)
        tvPublicationDate = findViewById(R.id.tv_nytm_detail_publicationDate)
        tvUpdatedDate = findViewById(R.id.tv_nytm_detail_updatedDate)
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
                UiUtil.setTintFavoriteIcon(context.resources, ivFavorite, isFavorite)
                tvTitle!!.text = movie!!.title

                tvByLine!!.text =
                    resources.getString(R.string.byLine, movie!!.byLine)
                tvHeadLine!!.text = movie!!.headLine
                tvSummaryShort!!.text =
                    resources.getString(R.string.tv_nytm_detail_summaryShort, movie!!.mpaaRating)
                tvLink!!.text = resources.getString(
                    R.string.tv_nytm_detail_link,
                    movie!!.suggested_link_text
                )
                tvLink!!.setOnClickListener {
                    callingExternalNavigatorWeb(movie?.urlReview)
                }
                tvPublicationDate!!.text = resources.getString(
                    R.string.tv_nytm_detail_publicationDate,
                    movie!!.publicationDate
                )
                tvUpdatedDate!!.text = resources.getString(
                    R.string.tv_nytm_detail_updatedDate,
                    movie!!.dateUpdated
                )

                UiUtil.loadPicture(ivDetail, movie!!.urlPicture)

                ivFavorite!!.setOnClickListener {
                    ivFavorite!!.isEnabled = false
                    setFavorite()
                    movieViewModel.updateSetFavorite(context, movie!!.Id!!, isFavorite)
                    ivFavorite!!.isEnabled = true
                }

                ivShare!!.setOnClickListener {
                    shareMovieForExternalApp(movie!!.urlReview)
                }
            }
        }

    private fun setFavorite() {
        if (isFavorite) {
            isFavorite = false
            Toast.makeText(
                context,
                resources.getString(R.string.toast_unfavorite, movie!!.title),
                Toast.LENGTH_LONG
            ).show()
        } else {
            isFavorite = true
            Toast.makeText(
                context,
                resources.getString(R.string.toast_favorite, movie!!.title),
                Toast.LENGTH_LONG
            )
                .show()
        }
        UiUtil.setTintFavoriteIcon(context.resources, ivFavorite, isFavorite)
    }

    private fun shareMovieForExternalApp(uriReview: String) {
        if (!uriReview.isBlank()) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$uriReview by NYTMovies")
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(sendIntent, resources.getString(R.string.chooser_your_sharedApp))
            startActivity(shareIntent)

        } else {
            Toast.makeText(
                context,
                resources.getString(R.string.toast_content_notAvail),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun callingExternalNavigatorWeb(uriReview: String?) {
        if (!uriReview.isNullOrBlank()) {
            val browserIntent: Intent = Uri.parse(uriReview).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            val chooser = Intent.createChooser(
                browserIntent,
                resources.getString(R.string.chooser_your_webNav)
            )

            val activities: List<ResolveInfo> = packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            val isIntentSafe: Boolean = activities.isNotEmpty()
            if (isIntentSafe) {
                startActivity(chooser)
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.toast_noHaveBrowser),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                context,
                resources.getString(R.string.toast_content_notAvail),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onBackPressed() {
        backToMovieList(this)
    }
}