package com.michelbarbosa.nytmovies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.model.Movie
import com.michelbarbosa.nytmovies.ui.NYTMoviesAdapter.NYTMoviesViewHolder
import java.util.*

class NYTMoviesAdapter(private val movieClickListener: ItemMovieClickListener?) :
    RecyclerView.Adapter<NYTMoviesViewHolder>() {
    private var movieList: List<Movie>?

    companion object;
    init {
        movieList = ArrayList()
    }

    fun setMovieList(movieList: List<Movie>?) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NYTMoviesViewHolder {
        return NYTMoviesViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.nytmovie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NYTMoviesViewHolder, position: Int) {
        holder.bind(movieList!![position])
    }

    override fun getItemCount(): Int {
        return if (movieList != null && movieList!!.isNotEmpty()) movieList!!.size else 0
    }

    inner class NYTMoviesViewHolder(itemView: View) :
        ViewHolder(itemView) {
        private var movie: Movie? = null
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)

        fun bind(movie: Movie) {
            this.movie = movie
            tvTitle.text = movie.title
        }

        init {
            itemView.setOnClickListener {
                movie?.let { it1 -> movieClickListener?.onClickMovie(it1) }
            }
        }
    }

}