package com.kuzmin.service

import com.kuzmin.dto.MediaResponseDto
import com.kuzmin.model.MediaType
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class FileService (private val uploadPath: String, val uploadUserPath: String) {
    private val images = listOf(ContentType.Image.JPEG, ContentType.Image.PNG)

    init {
        println(Paths.get(uploadPath).toAbsolutePath().toString())
        if (Files.notExists(Paths.get(uploadPath))) {
            Files.createDirectory(Paths.get(uploadPath))
        }
        println(Paths.get(uploadUserPath).toAbsolutePath().toString())
        if (Files.notExists(Paths.get(uploadUserPath))) {
            Files.createDirectory(Paths.get(uploadUserPath))
        }
    }

    suspend fun save(multipart: MultiPartData, users: Boolean = false): MediaResponseDto {
        var response: MediaResponseDto? = null
        multipart.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    if (part.name == "file") {
                        // TODO: use Apache Tika for content detection
                        if (!images.contains(part.contentType)) {
                            throw UnsupportedMediaTypeException(part.contentType ?: ContentType.Any)
                        }
                        val ext = when (part.contentType) {
                            ContentType.Image.JPEG -> "jpg"
                            ContentType.Image.PNG -> "png"
                            else -> throw UnsupportedMediaTypeException(part.contentType!!)
                        }
                        val name = "${UUID.randomUUID()}.$ext"
                        val path = Paths.get(uploadPath, name)
                        val pathUser = Paths.get(uploadUserPath, name)

                        if(users == true){
                            part.streamProvider().use {
                                withContext(Dispatchers.IO) {
                                    Files.copy(it, pathUser)
                                }
                            }
                        } else {
                            part.streamProvider().use {
                                withContext(Dispatchers.IO) {
                                    Files.copy(it, path)
                                }
                            }
                        }

                        part.dispose()
                        response = MediaResponseDto(name, MediaType.IMAGE)
                        return@forEachPart
                    }
                }
            }

            part.dispose()
        }
        return response ?: throw BadRequestException("No file field in request")
    }
}