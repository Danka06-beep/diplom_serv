package com.kuzmin.dto

import com.kuzmin.model.AttachmentModel
import com.kuzmin.model.UserModel

data class AuthorPostRequest (
    val id: Long? = 0,
    val username: String? = null,
    val attachment: AttachmentModel? = null,
    val readOnly: Boolean = false,
) {
    companion object {
        fun fromModel(model: UserModel) = AuthorPostRequest(
            id = model.id,
            username = model.username,
            attachment = model.attachment,
            readOnly = model.readOnly,
        )
    }
}