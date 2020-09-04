package com.michelbarbosa.nytmovies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.ui.NYTMoviesAdapter.NYTMoviesViewHolder
import com.michelbarbosa.nytmovies.util.UiUtil
import kotlin.collections.ArrayList

class NYTMoviesAdapter(private val movieClickListener: ItemMovieClickListener?) :
    RecyclerView.Adapter<NYTMoviesViewHolder>(), Filterable {

    private var movieList: List<Movie>?
    private lateinit var movieListFull: MutableList<Movie>

    companion object;
    init {
        movieList = ArrayList()
    }

    fun setMovieList(movieList: List<Movie>?) {
        this.movieList = movieList as MutableList<Movie>?
        if(movieList != null){
            movieListFull = ArrayList(movieList)
        }
        notifyDataSetChanged()
    }

    fun getMovieList(): List<Movie>?{
        return if (movieList!!.isNotEmpty())
            this.movieList
        else{
            null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NYTMoviesViewHolder {
        return NYTMoviesViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.nytm_item_of_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NYTMoviesViewHolder, position: Int) {
        holder.bind(movieList!![position])
    }

    override fun getItemCount(): Int {
        return if (movieList != null && movieList!!.isNotEmpty()) movieList!!.size else 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                movieList = filterResults.values as List<Movie>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    movieListFull
                else
                    movieListFull.filter {
                        it.title!!.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

    inner class NYTMoviesViewHolder(itemView: View) :
        ViewHolder(itemView) {
        private var movie: Movie? = null
        private val ivMovie: ImageView = itemView.findViewById(R.id.iv_item)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        private val byLine: TextView = itemView.findViewById(R.id.tv_item_byLine)
        private val headLine: TextView = itemView.findViewById(R.id.tv_item_headLine)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.iv_item_favorite)

        fun bind(movie: Movie) {
            this.movie = movie
            UiUtil.loadPicture(ivMovie, movie.urlPicture)
            tvTitle.text = movie.title
            byLine.text = movie.byLine
            headLine.text = movie.headLine
            UiUtil.setTintFavoriteIcon(itemView.resources, ivFavorite, movie.favorite)
        }

        init {
            itemView.setOnClickListener {
                movie?.let { it1 -> movieClickListener?.onClickMovie(it1) }
            }
        }
    }




}