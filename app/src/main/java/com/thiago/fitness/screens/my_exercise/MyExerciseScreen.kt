package com.thiago.fitness.screens.my_exercise

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.thiago.fitness.domain.model.Training
import com.thiago.fitness.presentation.navigation.DetailsScreen
import com.thiago.fitness.screens.my_exercise.components.GetExerciseByIdUser


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyExerciseScreen(navController: NavHostController, trainingId: String,viewModel: MyExerciseViewModel = hiltViewModel()) {

    viewModel.getExercises(trainingId)
    Scaffold(
        content = {
            GetExerciseByIdUser(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 50.dp),
                onClick = {


                    //navController.navigate("training/exercise/new/$trainingId")
                    navController.navigate(DetailsScreen.NewExercise.createRoute(trainingId))

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    )

}