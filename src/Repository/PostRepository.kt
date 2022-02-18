package com.kuzmin.Repository

import com.kuzmin.model.AttachmentModel
import com.kuzmin.model.PostModel
import com.kuzmin.model.RepostModel
import com.kuzmin.model.UserModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun save(item: PostModel): PostModel
    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long, user: Long?): PostModel?
    suspend fun dislikeById(id: Long, user: Long?): PostModel?
    suspend fun new(txt: String?, author: UserModel?): List<PostModel>
    suspend fun repost(item: RepostModel): PostModel?
    suspend fun getfive():List<PostModel>
    suspend fun getOld(id: Long): List<PostModel>
    suspend fun newPost(txt: String?, attachment: AttachmentModel?, autorName: UserModel?): List<PostModel>
}