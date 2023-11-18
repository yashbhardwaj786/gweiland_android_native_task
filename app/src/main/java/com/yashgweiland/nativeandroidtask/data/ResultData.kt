package com.yashgweiland.nativeandroidtask.data

import com.squareup.moshi.Json

data class ResultData(
    @field:Json(name = "status") val status: StatusResult? = null,
    @field:Json(name = "data") val data: ArrayList<CryptoDataResponse>? = null
)

data class StatusResult(
    @field:Json(name = "timestamp") val timestamp: String? = null,
    @field:Json(name = "error_code") val errorCode: Int? = null,
    @field:Json(name = "error_message") val errorMessage: Int? = null,
)

data class CryptoDataResponse(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "symbol") val symbol: String? = null,
    @field:Json(name = "slug") val slug: String? = null,
    @field:Json(name = "quote") val quote: CryptoDataQuotes? = null,
)
data class CryptoDataQuotes(
    @field:Json(name = "USD") val usd: CryptoDataQuotesUSD? = null,
)
data class CryptoDataQuotesUSD(
    @field:Json(name = "price") val price: Double? = null,
    @field:Json(name = "percent_change_24h") val percentChange: Double? = null,
)
