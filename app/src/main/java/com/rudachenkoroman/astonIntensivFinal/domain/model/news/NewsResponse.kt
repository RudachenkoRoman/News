package com.rudachenkoroman.astonIntensivFinal.domain.model.news

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)