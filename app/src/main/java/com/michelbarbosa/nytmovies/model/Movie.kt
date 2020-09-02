package com.michelbarbosa.nytmovies.model

import java.io.Serializable

class Movie(val title: String, val mpaaRating: String, val headLine: String, val summaryShort: String,
            val publicationDate: String, val dateUpdated: String, val suggested_link_text: String,
            val urlReview: String, val urlPicture: String) : Serializable