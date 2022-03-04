package com.techforline.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.techforline.models.User
import com.techforline.models.UserInput
import com.techforline.services.AuthService

fun SchemaBuilder.authSchema(authService: AuthService) {

    mutation("signIn") {
        description = "Authenticate an existing user"

        resolver { userInput: UserInput ->
            try {
                authService.signIn(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("signUp") {
        description = "Authenticate a new user"

        resolver { userInput: UserInput ->
            try {
                authService.signUp(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    type<User> {
        User::hashPass.ignore()
    }
}