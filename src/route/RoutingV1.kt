package com.kuzmin.route

import com.kuzmin.Repository.PostRepository
import com.kuzmin.Repository.UserRepository
import com.kuzmin.dto.AuthenticationRequestDto
import com.kuzmin.dto.UserResponseDto
import com.kuzmin.model.UserModel
import com.kuzmin.service.FCMService
import com.kuzmin.service.FileService
import com.kuzmin.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

class RoutingV1(val userService : UserService, private val staticPath: String, private val fileService: FileService, private val fcmService: FCMService) {
    fun setup(configuration: Routing) {
        with(configuration) {
            val repo by kodein().instance<PostRepository>()
            val repos by kodein().instance<UserRepository>()
            route("/api/v1") {
                static("/static") {
                    files(staticPath)
                }
                get("/") {
                    call.respondText("Server working", ContentType.Text.Plain)
                }
                authenticate {
                    get("/me") {
                        val me = call.authentication.principal<UserModel>()
                        call.respond(UserResponseDto.fromModel(me!!))
                    }
                }
                post("/authentication") {
                    val input = call.receive<AuthenticationRequestDto>()
                    println("authentication $input")
                    val response = userService.authenticate(input)
                    println("authentication $response")
                    call.respond(response)
                }
                post("/registration") {
                    val input = call.receive<AuthenticationRequestDto>()
                    val response = userService.addUser(input.username, input.password)
                    call.respond(response)
                }
            }
        }
    }
}