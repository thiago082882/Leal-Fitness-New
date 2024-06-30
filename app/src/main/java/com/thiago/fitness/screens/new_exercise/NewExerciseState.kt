package com.thiago.fitness.screens.new_exercise

import com.google.firebase.Timestamp
import com.thiago.fitness.domain.model.Training

data class NewExerciseState(
 val trainingId: String = "",
 val name: String = "",
 val remarks: String = "",
 var image: String = "",
 val sets: Int = 0,
 val repetitions: Int = 0,
 val restTimeSeconds: Int = 0



)