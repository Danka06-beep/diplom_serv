package com.kuzmin.model

data class MediaModel(val id: String, val mediaType: MediaType){

}

enum class MediaType {
    IMAGE
}