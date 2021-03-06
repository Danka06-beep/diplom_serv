package com.kuzmin.model

import io.ktor.auth.*

data class UserModel(
    val id: Long = 0,
    val username: String,
    val password: String,
    val token: String,
    val tokenDevice: String = " ",
    val userStatus: Long = 0,
    val attachment: AttachmentModel? = null,
    val readOnly: Boolean = false,
    ): Principal