package com.kuzmin.model

import com.kuzmin.dto.AuthorPostRequest

data class LikeAndDislikeModel(val autor: AuthorPostRequest, val date: Long, val type: TypeLIke) {
}

enum class TypeLIke {
    LIKE, DISLIKE
}