package com.kuzmin.service

import com.kuzmin.Exception.PasswordChangeException
import com.kuzmin.Exception.UseraddException
import com.kuzmin.Repository.UserRepository
import com.kuzmin.dto.AuthenticationRequestDto
import com.kuzmin.dto.AuthenticationResponseDto
import com.kuzmin.dto.UserResponeDto
import com.kuzmin.model.UserModel
import io.ktor.features.*
import org.springframework.security.crypto.password.PasswordEncoder

class UserService(
    private val repo: UserRepository,
    private val tokenService: JWTTokenService,
    private val passwordEncoder: PasswordEncoder
) {
    suspend fun getModelById(id: Long): UserModel? {
        return repo.getById(id)
    }
    suspend fun authenticate(input: AuthenticationRequestDto): AuthenticationResponseDto {
        val model = repo.getByUsername(input.username) ?: throw NotFoundException()
        if (!passwordEncoder.matches(input.password, model.password)) {
            throw PasswordChangeException("Неверный пароль")
        }

        val token = tokenService.generate(model.id)
        return AuthenticationResponseDto(token)
    }
    suspend fun addUser(username: String, password: String): AuthenticationResponseDto {
        println("adduser")
        println("id ${repo.getSizeListUser()}")
        println("username $username")
        println("password ${passwordEncoder.encode(password)}")
        println("token ${tokenService.generate(repo.getSizeListUser().toLong())}")
        try {
            val  model = UserModel(
                id = repo.getSizeListUser(),
                username = username,
                password = passwordEncoder.encode(password),
                token = tokenService.generate(repo.getSizeListUser().toLong())
            )

            println("model $model")
            println("model $model")
            val checkingIsUser = repo.addUser(model)
            if (checkingIsUser) {
                val token = tokenService.generate(model.id)
                return AuthenticationResponseDto(token)
            }
        } catch (e: Exception){
            print("exception $e")
        }

        return throw UseraddException("Такой логин уже зарегистрирован")
    }
    suspend fun addTokenDevice(tokenUser: String, tokenDevice: String): UserResponeDto {
        return UserResponeDto(repo.addTokenDevice(tokenUser, tokenDevice))
    }
    fun findTokenDevice(input: AuthenticationRequestDto):String{
        val tokenDevice = repo.findTokenDevice(input.username)
        return tokenDevice
    }
    fun findTokenDeviceUser(input: String):String{
        val tokenDevice = repo.findTokenDevice(input)
        return tokenDevice
    }
    suspend fun IdTokenDivivce(id: Long?, tokenDevice: String): Boolean {
        return (repo.addIdTokenDivivce(id,tokenDevice))
    }
    suspend fun changePassword(old: String, new: String, id: Long) {

        val model = repo.getById(id) ?: throw NotFoundException()
        if (!passwordEncoder.matches(old, model.password)) {
            throw PasswordChangeException("Неверный пароль")
        }
        val copy = model.copy(password = passwordEncoder.encode(new))
        repo.save(copy)
    }
}