package com.michelbarbosa.nytmovies.data.dao.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertMovieList(movieList : List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getAllMovies() : LiveData<List<Movie>>

}