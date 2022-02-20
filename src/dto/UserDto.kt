package com.kuzmin.dto

import com.kuzmin.model.AttachmentModel
import com.kuzmin.model.UserModel

data class UserDto(

    val id: Long = 0,
    val username: String? = null,
    val userStatus: Long = 0,
    val attachment: AttachmentModel?,
    val readOnly: Boolean = false,
    ){
    companion object{
        fun fromUserDto(user: UserModel) = UserDto(
            id = user.id,
            username = user.username,
            userStatus = user.userStatus,
            attachment = user.attachment,
            readOnly = user.readOnly
        )
    }
}
