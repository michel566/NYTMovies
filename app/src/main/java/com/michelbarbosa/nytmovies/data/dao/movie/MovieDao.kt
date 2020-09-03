package com.michelbarbosa.nytmovies.data.dao.movie

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertMovieList(movieList : List<Movie>)

    @Query("UPDATE Movie SET favorite = :favorite WHERE id = :id")
    fun UpdateSetFavorite(id: Int, favorite: Boolean)

    @Query("SELECT DISTINCT * FROM Movie ORDER BY date_updated DESC")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("SELECT DISTINCT * FROM Movie ORDER BY date_updated DESC")
    fun getResult() : List<Movie>

}