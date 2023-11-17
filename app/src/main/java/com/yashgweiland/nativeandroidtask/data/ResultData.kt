package com.yashgweiland.nativeandroidtask.data

import com.squareup.moshi.Json

data class ResultData(
    @field:Json(name = "joke") val joke: String = ""
)
