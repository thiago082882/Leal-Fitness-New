package com.thiago.fitness.screens.new_exercise

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiago.fitness.domain.model.Exercise
import com.thiago.fitness.domain.model.Response
import com.thiago.fitness.domain.use_cases.exercise.ExerciseUseCases
import com.thiago.fitness.presentation.utils.ComposeFileProvider
import com.thiago.fitness.presentation.utils.ResultingActivityHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewExerciseViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel() {

    var state by mutableStateOf(NewExerciseState())
    var file: File? = null
    val resultingActivityHandler = ResultingActivityHandler()
    var createExerciseResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    fun createExercise(exercise: Exercise, file: File) = viewModelScope.launch {
        createExerciseResponse = Response.Loading
        val result = exerciseUseCases.createExercise(exercise, file)
        createExerciseResponse = result
    }



    fun onNewExercise(trainingId: String) {
        val exercise = Exercise(
            name = state.name,
            remarks = state.remarks,
            image = state.image,
            sets = state.sets,
            repetitions = state.repetitions,
            restTimeSeconds = state.restTimeSeconds,
            trainingId =  trainingId
        )
        createExercise(exercise,file!!)
    }

    fun pickImage() = viewModelScope.launch {
        val result = resultingActivityHandler.getContent("image/*")
        if (result != null) {
            file = ComposeFileProvider.createFileFromUri(context, result)
            state = state.copy(image = result.toString())
        }
    }

    fun takePhoto() = viewModelScope.launch {
        val result = resultingActivityHandler.takePicturePreview()
        if (result != null) {
            state = state.copy(image = ComposeFileProvider.getPathFromBitmap(context, result))
            file = File(state.image)
        }
    }

    fun clearForm() {
        state = state.copy(
            name = "",
            remarks = "",
            image = ""

        )
        createExerciseResponse = null
    }

    fun onNameInput(name: String) {
        state = state.copy(name = name)
    }


    fun onRemarksInput(remarks: String) {
        state = state.copy(remarks = remarks)
    }

    fun onImageInput(image: String) {
        state = state.copy(image = image)
    }
    fun onSetsInput(sets: Int) {
        state = state.copy(sets = sets)
    }

    fun onRepetitionsInput(repetitions: Int) {
        state = state.copy(repetitions = repetitions)
    }

    fun onRestTimeSecondsInput(restTimeSeconds: Int) {
        state = state.copy(restTimeSeconds = restTimeSeconds)
    }
}
