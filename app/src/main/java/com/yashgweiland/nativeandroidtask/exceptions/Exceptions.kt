package com.yashgweiland.nativeandroidtask.exceptions

import java.io.IOException
import java.net.SocketTimeoutException

class NoInternetException(message: String) : IOException(message)
class TimeoutException(message: String) : SocketTimeoutException(message)