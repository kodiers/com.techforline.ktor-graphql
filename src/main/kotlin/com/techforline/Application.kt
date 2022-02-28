package com.techforline

import com.apurebase.kgraphql.GraphQL
import com.techforline.graphql.dessertSchema
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(GraphQL) {
        playground = true
        schema {
            dessertSchema()
        }
    }
}