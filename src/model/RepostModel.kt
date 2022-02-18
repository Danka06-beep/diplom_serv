package com.kuzmin.model

data class RepostModel(
    val id: Long = 0,
    val authorRepost: UserModel?,
    val txtRepost: String? = null,
){

}