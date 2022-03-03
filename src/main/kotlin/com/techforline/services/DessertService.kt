package com.techforline.services

import com.mongodb.client.MongoClient
import com.techforline.models.Dessert
import com.techforline.models.DessertInput
import com.techforline.models.DessertsPage
import com.techforline.repository.DessertRepository
import com.techforline.repository.ReviewRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class DessertService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo: DessertRepository = DessertRepository(client)
    private val reviewRepo = ReviewRepository(client)

    fun getDessertsPage(page: Int, size: Int): DessertsPage {
        return repo.getDessertsPage(page, size)
    }

    fun getDessert(id: String): Dessert {
        val dessert = repo.getById(id)
        dessert.reviews = reviewRepo.getReviewsByDessertId(dessertId = id)
        return dessert
    }

    fun createDessert(dessertInput: DessertInput, userId: String): Dessert {
        val uid = UUID.randomUUID().toString()
        val dessert = Dessert(
            id = uid,
            userId = userId,
            name = dessertInput.name,
            description = dessertInput.description,
            imageUrl = dessertInput.imageUrl
        )
        return repo.add(dessert)
    }

    fun updateDessert(userId: String, dessertId: String, dessertInput: DessertInput): Dessert {
        val dessert = repo.getById(dessertId)
        if (dessert.userId == userId) {
            val updates = Dessert(
                id = dessertId,
                userId = userId,
                name = dessertInput.name,
                description = dessertInput.description,
                imageUrl = dessertInput.imageUrl
            )
            return repo.update(updates)
        }
        error("Cannot update dessert")
    }

    fun deleteDesert(userId: String, dessertId: String): Boolean {
        val dessert = repo.getById(dessertId)
        if (dessert.userId == userId) {
            return repo.delete(dessertId)
        }
        error("Cannot delete desert")
    }
}