package com.kuzmin.dto

import com.kuzmin.model.AttachmentModel

data class AuthorPostResponseDto
    (
    val id: Long = 0,
    val username: String,
    val attachment: AttachmentModel? = null,
    val readOnly: Boolean = false,
) {
}