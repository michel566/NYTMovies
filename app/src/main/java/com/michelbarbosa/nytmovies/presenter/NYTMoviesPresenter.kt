package com.michelbarbosa.nytmovies.presenter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.enums.ErrorType
import com.michelbarbosa.nytmovies.mapper.NYTMovieMapper
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.network.RetrofitClientInstance
import com.michelbarbosa.nytmovies.network.response.NYTMoviesResponse
import com.michelbarbosa.nytmovies.util.UiUtil
import com.michelbarbosa.nytmovies.util.Util
import michel566.androidmodules.lightdialog.DialogType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NYTMoviesPresenter(private var view: NYTMoviesContract.ShowMoviesView?) :
    NYTMoviesContract.NYTMoviesPresenter {

    private val API_KEY = "h0QvQQzJAkUgUGPEu7ymQUwNsBOxCWb8"

    override fun getAllMovies(context: Context, query: String, numOfPages: Int) {
        getAllReviewMovie(
            context, RetrofitClientInstance.instance!!.getAllReviewMovies
                (query, API_KEY, Util.changeNumPagesInOffset(numOfPages))
        )
    }

    fun getAllReviewMovie(context: Context, call: Call<NYTMoviesResponse>) {
        val progressDialog: AlertDialog =
            UiUtil.progressDialog(context, "Enviando Solicitação...")
        progressDialog.show()
        call.enqueue(object : Callback<NYTMoviesResponse> {
            override fun onResponse(
                call: Call<NYTMoviesResponse>,
                response: Response<NYTMoviesResponse>
            ) {
                progressDialog.dismiss()
                if (!response.isSuccessful) {
                    errorResponse(context, response)
                } else {
                    if (response.body() != null) {
                        val movieList: List<Movie> = NYTMovieMapper.movieMapper(response.body())
                        view!!.showMovies(movieList)
                    } else {
                        errorResponse(context, response)
                    }
                }
            }

            override fun onFailure(call: Call<NYTMoviesResponse>, t: Throwable) {
                progressDialog.dismiss()
                if (t.toString().contains(context.getResources().getString(R.string.exception_timeout))) {
                    view!!.showError(ErrorType.TIMEOUT, DialogType.ALERT)
                } else if (t.toString()
                        .contains(context.getResources().getString(R.string.exception_json_objreturn))
                ) {
                    view!!.showError(ErrorType.JSON_OBJ_RETURN, DialogType.ERROR)
                } else {
                    view!!.showError(ErrorType.DEFAULT, DialogType.ERROR)
                }
                Log.i("--> onFailure: ", call.request().toString())
                Log.i("--> Exception: ", t.toString())
            }
        })
    }

    fun errorResponse(context: Context, response: Response<*>) {
        view!!.showError(ErrorType.RESPONSE, DialogType.ALERT)
        Util.errorResponseCodeMessageAlert(context, response)
    }

}