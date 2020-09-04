package com.michelbarbosa.nytmovies.util

enum class ConnectionQualityEnum(private val networkPower: Int) {
    NONE(1), POOR(2), WEAK(3), MODERATE(4), MEDIUM(5), GOOD(6), STRONG(7), EXCELENT(8), FULL(9), UNKNOWN(
        0
    );

    fun asNetworkPower(): Int {
        return networkPower
    }

}