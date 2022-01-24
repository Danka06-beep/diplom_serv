package com.kuzmin.Exception

import java.lang.RuntimeException

class ActionProhibitedException(message: String): RuntimeException(message) {
}