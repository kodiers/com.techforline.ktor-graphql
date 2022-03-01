package com.techforline

import com.apurebase.kgraphql.GraphQL
import com.techforline.di.mainModule
import com.techforline.graphql.dessertSchema
import com.techforline.services.DessertService
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
        playground = true
        schema {
            dessertSchema(dessertService)
        }
    }
}