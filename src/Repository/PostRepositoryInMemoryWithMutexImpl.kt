package com.kuzmin.Repository

import com.google.gson.Gson
import com.kuzmin.Exception.ActionProhibitedException
import com.kuzmin.PostData
import com.kuzmin.model.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

class PostRepositoryInMemoryWithMutexImpl: PostRepository {

    private var nextId = 1L
    private val items = PostData.getDataBase()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> =
        mutex.withLock {
            for (post in items) {
                val index = items.indexOf(post)
                val copy = post.copy(viewPost = post.viewPost + 1)
                items[index] = copy
            }
            items.reversed()
        }


    override suspend fun getById(id: Long): PostModel? {
        return items.find { it.id == id }
    }

    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
            -1 -> {
                val copy = item.copy(id = nextId++)
                items.add(copy)
                File("post.json").writeText(Gson().toJson(items))
                copy
            }
            else -> {
                items[index] = item
                File("post.json").writeText(Gson().toJson(items))
                item
            }
        }
    }

    override suspend fun removeById(id: Long) {
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Long, user: Long?): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }
            val post = items[index]
            if (post.postLike.contains(user)) {
                return throw ActionProhibitedException("действие запрешено")
            }
            val newPost = post.copy(likeTxt = post.likeTxt.inc(), like = true)
            items[index] = newPost
            File("post.json").writeText(Gson().toJson(items))
            items
            newPost
        }


    override suspend fun dislikeById(id: Long, user: Long?): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return@withLock null
            }
            val post = items[index]
            if (post.postLike.contains(user)) {
                return throw ActionProhibitedException("действие запрешено")
            }
            val newPost = post.copy(dislikeTxt = post.dislikeTxt.inc(), dislike = true)
            items[index] = newPost
            File("post.json").writeText(Gson().toJson(items))
            items
            newPost
        }


    override suspend fun new(txt: String?, author: String?): List<PostModel> =
    mutex.withLock {
        val new = PostModel(id = items.size.toLong(), txt = txt, author = author)
        items.add(new)
        File("post.json").writeText(Gson().toJson(items))
        items
    }


    override suspend fun repost(item: RepostModel): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == item.id }
            println(index)
            if (index < 0){
                return@withLock null
            }
            val repost = items[index]
            val newRepost = repost.copy(id = items.size.toLong(),author = item.authorRepost,txt = item.txtRepost,type = PostType.Reposts, repost = repost )
            items.add(newRepost)
            File("post.json").writeText(Gson().toJson(items))
            newRepost
        }


    override suspend fun getfive(): List<PostModel> =
        mutex.withLock {
            File("post.json").writeText(Gson().toJson(items))
            items.takeLast(5).reversed()
        }


    override suspend fun getOld(id: Long): List<PostModel> =
        mutex.withLock {
            File("post.json").writeText(Gson().toJson(items))
            items.filter {
                it.id < id
            }.reversed()
        }


    override suspend fun newPost(txt: String?, attachment: AttachmentModel?, autorName: UserModel?): List<PostModel> =
        mutex.withLock {
            val newPost = PostModel(id = items.size.toLong(), txt = txt, attachment = attachment,author = autorName!!.username, autorId = autorName.id)
            items.add(newPost)
            File("post.json").writeText(Gson().toJson(items))
            items
        }
    }
