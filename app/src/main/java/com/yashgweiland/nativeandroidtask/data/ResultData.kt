package com.yashgweiland.nativeandroidtask.data


data class ResultData(
    val status: StatusResult? = null,
    val data: ArrayList<CryptoDataResponse>? = null
)

data class StatusResult(
    val timestamp: String? = null,
    val error_code: Int? = null,
    val error_message: Int? = null,
)

data class CryptoDataResponse(
    val id: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val slug: String? = null,
    val quote: CryptoDataQuotes? = null,
)

data class CryptoDataQuotes(
    val USD: CryptoDataQuotesUSD? = null,
)

data class CryptoDataQuotesUSD(
    val price: Double? = null,
    val percent_change_24h: Double? = null,
)
