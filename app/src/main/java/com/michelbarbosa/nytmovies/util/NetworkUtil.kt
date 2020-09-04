package com.michelbarbosa.nytmovies.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.michelbarbosa.nytmovies.R
import com.michelbarbosa.nytmovies.util.UiUtil.showDialog
import michel566.androidmodules.lightdialog.DialogType
import okhttp3.*
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkUtil {
    private val client = OkHttpClient()
    private const val TAG = "--> download "
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var fileSize: Long = 0
    private var kbps = 0

    fun isIdealConnection(
        context: Context,
        type: ConnectionQualityEnum,
        isShowMessage: Boolean
    ): Boolean {
        return if (networkQuality(context) >= type.asNetworkPower()) {
            true
        } else {
            networkStatus(context, isShowMessage)
            false
        }
    }

    private fun networkStatus(
        context: Context,
        isShowMessage: Boolean
    ) {
        if (checkStateNetwork(context.applicationContext) &&
            networkQuality(context) > ConnectionQualityEnum.NONE.asNetworkPower()
        ) {
            if (isShowMessage) {
                showDialog(
                    context,
                    context.resources.getString(R.string.msg_low_network),
                    DialogType.ALERT
                )
            }
        } else {
            if (isShowMessage) {
                showDialog(
                    context,
                    context.resources.getString(R.string.msg_no_network),
                    DialogType.ALERT
                )
            }
        }
    }

    private fun checkStateNetwork(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return !(info == null || !info.isConnected)
    }

    private fun networkQuality(context: Context): Int {
        return when (checkNetworkQuality(context)) {
            1 -> ConnectionQualityEnum.NONE.asNetworkPower()
            2 -> ConnectionQualityEnum.POOR.asNetworkPower()
            3 -> ConnectionQualityEnum.WEAK.asNetworkPower()
            4 -> ConnectionQualityEnum.MODERATE.asNetworkPower()
            5 -> ConnectionQualityEnum.MEDIUM.asNetworkPower()
            6 -> ConnectionQualityEnum.GOOD.asNetworkPower()
            7 -> ConnectionQualityEnum.STRONG.asNetworkPower()
            8 -> ConnectionQualityEnum.EXCELENT.asNetworkPower()
            9 -> ConnectionQualityEnum.FULL.asNetworkPower()
            0 -> ConnectionQualityEnum.UNKNOWN.asNetworkPower()
            else -> ConnectionQualityEnum.UNKNOWN.asNetworkPower()
        }
    }

    private fun checkNetworkQuality(context: Context): Int {
        var valueNetwork = 0
        if (checkStateNetwork(context)) {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            kbps = checkDownloadSpeed()
            if (info.isConnected && kbps != 0) {
                if (info.type == ConnectivityManager.TYPE_WIFI) {
                    valueNetwork = if (getWifiPowerLevel(context) > 2) {
                        getWifiPowerLevel(context) + getDownloadKbps(
                            kbps
                        )
                    } else {
                        getWifiPowerLevel(context)
                    }
                } else if (info.type == ConnectivityManager.TYPE_MOBILE) {
                    valueNetwork = if (getMobileLevel(info.subtype) > 2) {
                        getMobileLevel(info.subtype) + getDownloadKbps(
                            kbps
                        )
                    } else {
                        getMobileLevel(info.subtype)
                    }
                }
            } else {
                return 0
            }
        } else {
            return 0
        }
        return valueNetwork
    }

    private fun getDownloadKbps(kbps: Int): Int {
        return if (kbps > 20000) {
            4
        } else if (kbps > 5000) {
            3
        } else if (kbps > 2000) {
            2
        } else if (kbps > 150) {
            1
        } else if (kbps < 150) {
            0
        } else {
            0
        }
    }

    private fun getWifiPowerLevel(context: Context): Int {
        val wifiManager = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        var linkSpeed = 0
        if (wifiManager != null) {
            linkSpeed = wifiManager.connectionInfo.rssi
        }
        val level = WifiManager.calculateSignalLevel(linkSpeed, 5)
        return when (level) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 4
            5 -> 5
            0 -> 0
            else -> 0
        }
    }

    private fun getMobileLevel(subType: Int): Int {
        return when (subType) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> 0 // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_CDMA -> 0 // ~ 14-64 kbps
            TelephonyManager.NETWORK_TYPE_EDGE -> 1 // ~ 50-100 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> 1 // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A -> 1 // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_GPRS -> 1 // ~ 100 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA -> 3 // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA -> 2 // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA -> 3 // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS -> 3 // ~ 400-7000 kbps
            TelephonyManager.NETWORK_TYPE_EHRPD -> 2 // ~ 1-2 Mbps
            TelephonyManager.NETWORK_TYPE_EVDO_B -> 3 // ~ 5 Mbps
            TelephonyManager.NETWORK_TYPE_HSPAP -> 4 // ~ 10-20 Mbps
            TelephonyManager.NETWORK_TYPE_IDEN -> 0 // ~25 kbps
            TelephonyManager.NETWORK_TYPE_LTE -> 4 // ~ 10+ Mbps
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> 0
            else -> 0
        }
    }

    fun getNetworkValueQuality(context: Context): String {
        val networkSignal: String
        networkSignal = if (checkStateNetwork(context)) {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            if (info.isConnected) {
                "Quality: " + networkQuality(context)
            } else {
                return "No connection"
            }
        } else {
            return "No check state network"
        }
        return networkSignal + " | " + checkDownloadSpeed() + " Kbps"
    }

    private fun checkDownloadSpeed(): Int {
        val builder = client.newBuilder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                    TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                    TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
                )
                .build()
            builder.connectionSpecs(listOf(spec))
        }
        val clientBuilder = builder.build()
        val downloadURL = "https://picsum.photos/id/10/128/72"
        val download = Request.Builder()
            .url(downloadURL)
            .build()
        startTime = System.currentTimeMillis()
        clientBuilder.newCall(download).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                kbps = 0
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                val responseHeaders = response.headers
                var i = 0
                val size = responseHeaders.size
                while (i < size) {
                    Log.d(
                        TAG,
                        responseHeaders.name(i) + ": " + responseHeaders.value(i)
                    )
                    i++
                }
                val input = response.body!!.byteStream()
                fileSize = try {
                    val bos = ByteArrayOutputStream()
                    val buffer = ByteArray(1024)
                    while (input.read(buffer) != -1) {
                        bos.write(buffer)
                    }
                    val docBuffer = bos.toByteArray()
                    bos.size().toLong()
                } finally {
                    input.close()
                }
                endTime = System.currentTimeMillis()
                val timeTakenMills =
                    Math.floor(endTime - startTime.toDouble()) // time taken in milliseconds
                val timeTakenInSecs =
                    timeTakenMills / 1000 // divide by 1000 to get time in seconds
                val kilobytePerSec = Math.round(1024 / timeTakenInSecs).toInt()
                val speed =
                    Math.round(fileSize / timeTakenMills).toDouble()
                Log.d(TAG, "Time taken in secs: $timeTakenInSecs")
                Log.d(TAG, "Kb per sec: $kilobytePerSec")
                Log.d(TAG, "Download Speed in mb: $speed")
                Log.d(TAG, "File size in kb: $fileSize")
                kbps = kilobytePerSec
            }
        })
        return kbps
    }
}