package com.rudachenkoroman.astonIntensivFinal.model.news

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)