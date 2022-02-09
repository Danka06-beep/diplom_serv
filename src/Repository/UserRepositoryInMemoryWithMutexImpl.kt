package com.kuzmin.Repository

import com.google.gson.Gson
import com.kuzmin.UserData
import com.kuzmin.model.AttachmentModel
import com.kuzmin.model.UserModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

class UserRepositoryInMemoryWithMutexImpl: UserRepository {

    private var nextId = 1L
    private val items = UserData.getDataBase()
    private val mutex = Mutex()

    override suspend fun getAll(): List<UserModel> {
       mutex.withLock {
           return items.toList()
       }
    }

    override suspend fun getById(id: Long): UserModel? {
        mutex.withLock {
            return items.find { it.id == id }
        }
    }

    override suspend fun getByIds(ids: Collection<Long>): List<UserModel> {
        mutex.withLock {
            return items.filter { ids.contains(it.id) }
        }
    }

    override suspend fun getByUsername(username: String): UserModel? {
        mutex.withLock {
            return items.find { it.username == username }
        }
    }

    override suspend fun save(item: UserModel): UserModel {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val copy = item.copy(id = items.size.toLong())
                    items.add(copy)
                    File("user.json").writeText(Gson().toJson(items))
                    copy
                }
                else -> {
                    val copy = items[index].copy(username = item.username, password = item.password)
                    items[index] = copy
                    File("user.json").writeText(Gson().toJson(items))
                    copy
                }
            }
        }
    }

    override suspend fun addUser(item: UserModel): Boolean {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.username == item.username }) {
                -1 -> {
                    val copy = item.copy(id = items.size.toLong())
                    items.add(copy)
                    File("user.json").writeText(Gson().toJson(items))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override suspend fun getSizeListUser(): Long {
        return items.size.toLong()
    }

    override suspend fun addTokenDevice(tokenUser: String, tokenDevice: String): String {
        mutex.withLock {
            val index = items.indexOfFirst { it.token == tokenUser }
            val copyUser = items[index].copy(tokenDevice = tokenDevice)
            items[index] = copyUser
            File("user.json").writeText(Gson().toJson(items))
            return copyUser.username
        }
    }

    override fun findTokenDevice(username: String): String {
        val index = items.indexOfFirst { it.username == username }

        return items[index].tokenDevice
    }

    override suspend fun addIdTokenDivivce(id: Long?, tokenDevice: String): Boolean {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if(index != -1){
                val copyUser = items[index].copy(tokenDevice = tokenDevice)
                items[index] = copyUser
                File("user.json").writeText(Gson().toJson(items))
                return true
            }else{
                return false
            }
        }
    }

    override suspend fun ChangeImg(id: Long, attachmentModel: AttachmentModel): Boolean {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            val user = items[index]
            val copy = user.copy(attachment = attachmentModel)
            items[index] = copy
            File("user.json").writeText(Gson().toJson(items))
        }
        return true
    }

    override suspend fun ToReadOnly(id: Long?) {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            val user = items[index]
            val copy = user.copy(readOnly = true)
            items[index] = copy
            File("user.json").writeText(Gson().toJson(items))
        }
    }

    override suspend fun NotReadOnly(id: Long?) {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            val user = items[index]
            val copy = user.copy(readOnly = false)
            items[index] = copy
            File("user.json").writeText(Gson().toJson(items))
        }
    }

}

