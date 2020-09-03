package com.michelbarbosa.nytmovies.data.dao.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Movie")
data class Movie(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "mpaa_rating")
    var mpaaRating: String,

    @ColumnInfo(name = "byLine")
    var byLine: String,

    @ColumnInfo(name = "headline")
    var headLine: String,

    @ColumnInfo(name = "summary_short")
    var summaryShort: String,

    @ColumnInfo(name = "publication_date")
    var publicationDate: String,

    @ColumnInfo(name = "date_updated")
    var dateUpdated: String,

    @ColumnInfo(name = "suggested_link_text")
    var suggested_link_text: String,

    @ColumnInfo(name = "url_review")
    var urlReview: String,

    @ColumnInfo(name = "url_picture")
    var urlPicture: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean

) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}