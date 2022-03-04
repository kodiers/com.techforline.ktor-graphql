package com.techforline.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoClient
import com.techforline.models.User
import com.techforline.models.UserInput
import com.techforline.models.UserResponse
import com.techforline.repository.UserRepository
import io.ktor.application.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class AuthService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val secret: String = "secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun signIn(userInput: UserInput): UserResponse? {
        val user = repo.getUserByEmail(userInput.email) ?: error("No such user by that email")
        // hash incoming password and comparing with saved
        if (BCrypt.verifyer()
                .verify(userInput.password.toByteArray(Charsets.UTF_8), user.hashPass)
                .verified) {
            val token = signAccessToken(user.id)
            return UserResponse(token, user)
        }
        error("password is incorrect")
    }

    private fun signAccessToken(id: String): String {
        return JWT.create()
            .withIssuer("example")
            .withClaim("userId", id)
            .sign(algorithm)
    }

    fun signUp(userInput: UserInput): UserResponse? {
        val hashedPassword = BCrypt.withDefaults().hash(10, userInput.password.toByteArray(Charsets.UTF_8))
        val id = UUID.randomUUID().toString()
        val emailUser = repo.getUserByEmail(userInput.email)
        if (emailUser != null) {
            error("Email already in use")
        }
        val newUser = repo.add(User(id, userInput.email, hashedPassword))
        val token = signAccessToken(newUser.id)
        return UserResponse(token, newUser)
    }

    fun verifyToken(call: ApplicationCall): User? {
        return try {
            val authHeader = call.request.headers["Authorization"] ?: ""
            val token = authHeader.split("Bearer ").last()
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            return User(userId, email = "", hashPass = ByteArray(0))
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

}