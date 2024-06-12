package com.thiago.fitness.domain.repository

import com.thiago.fitness.domain.model.Exercise
import com.thiago.fitness.domain.model.Response
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ExerciseRepository {


    fun getExercisesByTrainingId(trainingId: String): Flow<Response<List<Exercise>>>
    suspend fun createExercise(exercise: Exercise, file: File): Response<Boolean>
    suspend fun updateExercise(exercise: Exercise, file: File?): Response<Boolean>
    suspend fun deleteExercise(exerciseId: String): Response<Boolean>


}