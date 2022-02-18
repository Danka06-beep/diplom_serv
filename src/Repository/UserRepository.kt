package com.kuzmin.Repository

import com.kuzmin.model.AttachmentModel
import com.kuzmin.model.UserModel

interface UserRepository {
    suspend fun getAll(): List<UserModel>
    suspend fun getById(id: Long): UserModel?
    suspend fun getByIds(ids: Collection<Long>): List<UserModel>
    suspend fun getByUsername(username: String): UserModel?
    suspend fun save(item: UserModel): UserModel
    suspend fun addUser(item: UserModel): Boolean
    suspend fun getSizeListUser(): Long
    suspend fun addTokenDevice(tokenUser: String, tokenDevice: String): String
    fun  findTokenDevice(username: String):String
    suspend fun addIdTokenDivivce(id : Long?, tokenDevice: String): Boolean
    suspend fun ChangeImg(id: Long, attachmentModel: AttachmentModel): Boolean
    suspend fun ToReadOnly(id: Long?)
    suspend fun NotReadOnly(id: Long?)
    suspend fun editAvatar(user: UserModel?, imageUser: AttachmentModel)
}