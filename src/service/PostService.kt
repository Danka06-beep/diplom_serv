package com.kuzmin.service

import com.kuzmin.Repository.PostRepository
import com.kuzmin.dto.PostRequestDto
import com.kuzmin.dto.PostResponseDto
import com.kuzmin.model.PostModel
import io.ktor.features.*

class PostService(val repo: PostRepository, val userService: UserService) {
    suspend fun getAll(): List<PostModel>{
       return repo.getAll().map {
           it.copy(authorAttachment = userService.getModelById(it.autorId))
       }

        }

    suspend fun getById(id: Long): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(input: PostRequestDto): PostResponseDto {
        val model = PostModel(id = input.id, author = input.author, txt = input.txt)
        return PostResponseDto.fromModel(repo.save(model))
    }

}