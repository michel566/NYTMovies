package com.michelbarbosa.nytmovies.enums

enum class ErrorType {

    TIMEOUT, NETWORK, JSON_OBJ_RETURN, RESPONSE, CURRENT_OBJ_NULL, DEFAULT;

    companion object {
        fun isErroType(erroType: String?): Boolean {
            return when (erroType) {
                "NETWORK", "TIMEOUT", "JSON_OBJ_RETURN", "RESPONSE", "CURRENT_OBJ_NULL", "DEFAULT" -> true
                else -> false
            }
        }
    }
}