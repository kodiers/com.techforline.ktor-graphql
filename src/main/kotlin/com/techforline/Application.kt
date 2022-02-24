package com.techforline

import com.apurebase.kgraphql.GraphQL
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.techforline.plugins.*
import io.ktor.application.*
import io.ktor.application.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(GraphQL) {
        playground = true
        schema {
            query("hello") {
                resolver { -> "World"}
            }
        }
    }
}