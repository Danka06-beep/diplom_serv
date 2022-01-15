package com.kuzmin.dto

import com.kuzmin.model.MediaModel
import com.kuzmin.model.MediaType

class MediaResponseDto (val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: MediaModel) = MediaResponseDto(
            id = model.id,
            mediaType = model.mediaType
        )
    }
}