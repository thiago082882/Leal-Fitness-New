package com.thiago.fitness.domain.use_cases.exercise

data class ExerciseUseCases(
    val createExercise: CreateExercise,
    val updateExercise: UpdateExercise,
    val deleteExercise: DeleteExercise,
    val getExercisesByTrainingUseCases: GetExercisesByUserIdUseCase
)