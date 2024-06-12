package com.thiago.fitness.screens.my_exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiago.fitness.domain.model.Exercise
import com.thiago.fitness.domain.model.Response
import com.thiago.fitness.domain.use_cases.exercise.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyExerciseViewModel @Inject constructor(
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel() {

    var exerciseResponse by mutableStateOf<Response<List<Exercise>>?>(null)
    var deleteResponse by mutableStateOf<Response<Boolean>?>(null)

    fun getExercises(trainingId: String) = viewModelScope.launch {
        exerciseResponse = Response.Loading
        exerciseUseCases.getExercisesByTrainingUseCases(trainingId).collect() { response ->
            exerciseResponse = response
        }
    }

    fun deleteExercise(exerciseId: String) = viewModelScope.launch {
        deleteResponse = Response.Loading
        val result = exerciseUseCases.deleteExercise(exerciseId)
        deleteResponse = result
    }
}
