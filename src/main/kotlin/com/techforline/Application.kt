package com.techforline

import com.apurebase.kgraphql.GraphQL
import com.techforline.di.mainModule
import com.techforline.graphql.authSchema
import com.techforline.graphql.dessertSchema
import com.techforline.graphql.profileSchema
import com.techforline.graphql.reviewSchema
import com.techforline.services.AuthService
import com.techforline.services.DessertService
import com.techforline.services.ProfileService
import com.techforline.services.ReviewService
import io.ktor.application.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        val dessertService = DessertService()
        val reviewService = ReviewService()
        val authService = AuthService()
        val profileService = ProfileService()

        playground = true

        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
            authSchema(authService)
            profileSchema(profileService)
        }
    }
}