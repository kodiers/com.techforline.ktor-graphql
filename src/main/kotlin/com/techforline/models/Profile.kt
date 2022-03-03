package com.techforline.models

data class Profile(val user: User, val desserts: List<Dessert> = emptyList())