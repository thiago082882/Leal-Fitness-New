package com.thiago.fitness.domain.use_cases.exercise

import com.thiago.fitness.domain.repository.ExerciseRepository
import javax.inject.Inject

class DeleteExercise @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(idExercise: String) = repository.deleteExercise(idExercise)
}