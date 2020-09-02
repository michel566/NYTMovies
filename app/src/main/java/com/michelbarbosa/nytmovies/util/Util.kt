package com.michelbarbosa.nytmovies.util

import android.content.Context
import android.os.Build
import com.michelbarbosa.nytmovies.R
import michel566.androidmodules.lightdialog.DialogType
import retrofit2.Response

object Util {

    fun checkminimalAPI(buildVersionCode: Int) : Boolean{
        return Build.VERSION.SDK_INT >= buildVersionCode
    }

    fun changeNumPagesInOffset(numPage: Int): String {
        if (numPage == 0){
            return ""
        } else{
            val value = numPage * 25
            return value.toString()
        }
    }

    fun errorResponseCodeMessageAlert(context: Context, response: Response<*>){
        when(response.code()){
            404 -> UiUtil.showDialog(context, R.string.msg_Erro404, DialogType.ALERT)
            500 -> UiUtil.showDialog(context, R.string.msg_Erro500, DialogType.ERROR)
            else -> UiUtil.showDialog(
                context,
                context.resources?.getString(R.string.msg_ErroGenerico)
                        + " Erro: " + response.code(), DialogType.ERROR
            )
        }
    }

}