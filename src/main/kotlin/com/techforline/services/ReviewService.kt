package com.techforline.services

import com.mongodb.client.MongoClient
import com.techforline.graphql.Review
import com.techforline.graphql.ReviewInput
import com.techforline.repository.ReviewRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class ReviewService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo = ReviewRepository(client)

    fun getReview(id: String): Review {
        return repo.getById(id)
    }

    fun createReview(userId: String, dessertId: String, reviewInput: ReviewInput): Review {
        val uuid = UUID.randomUUID().toString()
        val review = Review(
            id = uuid,
            userId = userId,
            dessertId = dessertId,
            text = reviewInput.text,
            rating = reviewInput.rating
        )
        return repo.add(review)
    }

    fun updateReview(userId: String, reviewId: String, reviewInput: ReviewInput): Review {
        val review = repo.getById(reviewId)
        if (review.userId == userId) {
            val updates = Review(
                id = review.id,
                dessertId = review.dessertId,
                userId = userId,
                text = reviewInput.text,
                rating = reviewInput.rating
            )
            return repo.update(updates)
        }
        error("Cannot update Review")
    }

    fun deleteReview(userId: String, reviewId: String): Boolean {
        val review = repo.getById(reviewId)
        if (review.userId == userId) {
            return repo.delete(reviewId)
        }
        error("Cannot delete review")
    }
}