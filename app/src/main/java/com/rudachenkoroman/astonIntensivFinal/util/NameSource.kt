package com.rudachenkoroman.astonIntensivFinal.util

private const val BBC_NEWS = "BBC News"
private const val C_N_N = "CNN"
private const val C_N_B_C = "CNBC"
private const val ABC_NEWS = "ABC News"
private const val ARS_TECH= "Ars Technica"
private const val AL_JAZEERA_EN= "Al Jazeera English"


enum class NameSource (val sourceName:String){
    BBC(BBC_NEWS),
    CNN(C_N_N),
    CNBC(C_N_B_C),
    ABCNEWS(ABC_NEWS),
    ARSTECH(ARS_TECH),
    ALJAZEERA_EN(AL_JAZEERA_EN)
}


