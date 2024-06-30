package com.thiago.fitness.domain.model


data class Exercise(

    var id: String = "",
    val trainingId: String = "",
    val name: String = "",
    val remarks: String = "",
    var image: String = "",
    val sets: Int = 0,
    val repetitions: Int = 0,
    val restTimeSeconds: Int = 0,
)
