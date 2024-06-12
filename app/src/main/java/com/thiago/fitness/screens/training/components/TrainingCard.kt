package com.thiago.fitness.screens.training.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.thiago.fitness.domain.model.Training
import com.thiago.fitness.presentation.navigation.DetailsScreen
import com.thiago.fitness.presentation.utils.formatTimestamp
import com.thiago.fitness.screens.training.TrainingViewModel


@Composable
    fun TrainingCard(navController: NavHostController, training: Training, viewModel: TrainingViewModel = hiltViewModel()) {
        Card(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    navController.navigate(
                        route = DetailsScreen.DetailTraining.passTraining(
                            training.toJson()
                        )
                    )
                },
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        model = training.image,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = training.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Username: ${training.user?.username ?: ""}",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Description: ${training.description}",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Date: ${formatTimestamp(training.data)}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

}