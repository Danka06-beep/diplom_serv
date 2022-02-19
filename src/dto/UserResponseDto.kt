package com.kuzmin.dto

import com.kuzmin.model.UserModel

class UserResponseDto (val id: Long = 0, val name: String?) {
    companion object {
        fun fromModel(model: UserModel) = UserResponseDto(id = model.id, name = model.username)
    }
}