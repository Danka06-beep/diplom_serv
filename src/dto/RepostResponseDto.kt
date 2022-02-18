package com.kuzmin.dto

import com.kuzmin.model.RepostModel
import com.kuzmin.model.UserModel

data class RepostResponseDto ( val id: Long = 0,
                          val authorRepost: UserModel? = null,
                          val txtRepost: String? = null) {
    companion object {
        fun fromRepostModel(model: RepostModel) = RepostResponseDto(
            id = model.id,
            authorRepost = model.authorRepost,
            txtRepost = model.txtRepost

        )
    }
}