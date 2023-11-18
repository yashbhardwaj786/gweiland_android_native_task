package com.yashgweiland.nativeandroidtask.data

import com.google.gson.annotations.SerializedName


data class ResultData(
    val status: StatusResult? = null,
    val data: ArrayList<CryptoDataResponse>? = null
)

data class StatusResult(
    val timestamp: String? = null,
    @SerializedName("error_code")
    val errorCode: Int? = null,
    @SerializedName("error_message")
    val errorMessage: String? = null,
)

data class CryptoDataResponse(
    val id: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val slug: String? = null,
    val quote: CryptoDataQuotes? = null,
)

data class CryptoDataQuotes(
    @SerializedName("USD")
    val usd: CryptoDataQuotesUSD? = null,
)

data class CryptoDataQuotesUSD(
    val price: Double? = null,
    @SerializedName("percent_change_24h")
    val percentChange: Double? = null,
)
