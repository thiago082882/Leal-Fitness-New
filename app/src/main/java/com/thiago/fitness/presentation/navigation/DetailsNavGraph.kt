package com.thiago.fitness.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.thiago.fitness.screens.detail_training.DetailTrainingScreen
import com.thiago.fitness.screens.my_exercise.MyExerciseScreen
import com.thiago.fitness.screens.new_exercise.NewExerciseScreen
import com.thiago.fitness.screens.new_training.NewTrainingScreen
import com.thiago.fitness.screens.profile_update.ProfileUpdateScreen
import com.thiago.fitness.screens.update_training.UpdateTrainingScreen

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.ProfileUpdate.route
    ) {
        composable(route = DetailsScreen.NewTraining.route) {
            NewTrainingScreen(navController = navController)
        }

        composable(
            route = DetailsScreen.NewExercise.route,
            arguments = listOf(navArgument("trainingId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getString("trainingId")
            if (trainingId != null) {
                NewExerciseScreen(navController = navController, trainingId = trainingId)
            }
        }

        composable(
            route = DetailsScreen.ProfileUpdate.route,
            arguments = listOf(navArgument("user") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user")
            if (user != null) {
                ProfileUpdateScreen(navController = navController, user = user)
            }
        }

        composable(
            route = DetailsScreen.DetailTraining.route,
            arguments = listOf(navArgument("training") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val training = backStackEntry.arguments?.getString("training")
            if (training != null) {
                DetailTrainingScreen(navController = navController, training = training)
            }
        }

        composable(
            route = DetailsScreen.UpdateTraining.route,
            arguments = listOf(navArgument("training") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val training = backStackEntry.arguments?.getString("training")
            if (training != null) {
                UpdateTrainingScreen(navController = navController, training = training)
            }
        }

        composable(
            route = DetailsScreen.ExerciseList.route + "/{trainingId}",
            arguments = listOf(navArgument("trainingId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getString("trainingId")
            if (trainingId != null) {
                MyExerciseScreen(navController = navController, trainingId = trainingId)
            }
        }

    }
}
sealed class DetailsScreen(val route: String) {

    data object NewTraining : DetailsScreen("training/new")


    data object NewExercise : DetailsScreen("training/exercise/new/{trainingId}")

    data object ProfileUpdate : DetailsScreen("profile/update/{user}") {
        fun passUser(user: String) = "profile/update/$user"
    }

    data object DetailTraining : DetailsScreen("training/detail/{training}") {
        fun passTraining(training: String) = "training/detail/$training"

    }


    data object UpdateTraining : DetailsScreen("training/update/{training}") {
        fun passTraining(training: String) = "training/update/$training"
    }

    data object ExerciseList : DetailsScreen("training/exercise") {
        fun passExercise(trainingId: String) = "training/exercise/$trainingId"
    }

}