package com.techforline.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.techforline.models.User
import com.techforline.services.ProfileService

fun SchemaBuilder.profileSchema(profileService: ProfileService) {
    query("getProfile") {
        resolver {ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                profileService.getProfile(userId)
            } catch (e: Exception) {
                null
            }
        }
    }
}