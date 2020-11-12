package dev.codingstoic.server.execption

import java.lang.Exception
import java.lang.RuntimeException

class SpringRedditException : RuntimeException {
    constructor(exceptionMessage: String) : super(exceptionMessage)
    constructor(exceptionMessage: String, exception: Exception) : super(exceptionMessage, exception)
}
