package com.thiago.fitness.data.repository

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import com.thiago.fitness.core.Constants.EXERCISE
import com.thiago.fitness.domain.model.Exercise
import com.thiago.fitness.domain.model.Response
import com.thiago.fitness.domain.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class ExerciseRepositoryImpl @Inject constructor(
    @Named(EXERCISE) private val exercisesRef: CollectionReference,
    @Named(EXERCISE) private val storageExerciseRef: StorageReference
) : ExerciseRepository {

    override fun getExercisesByTrainingId(trainingId: String): Flow<Response<List<Exercise>>> =
        callbackFlow {
            val snapshotListener = exercisesRef
                .whereEqualTo("trainingId", trainingId)
                .addSnapshotListener { snapshot, e ->

                    val exerciseResponse = if (snapshot != null) {
                        val listExercises = snapshot.toObjects(Exercise::class.java)
                        snapshot.documents.forEachIndexed { index, document ->
                            listExercises[index].id = document.id
                        }

                        Response.Success(listExercises)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(exerciseResponse)
                }

            awaitClose {
                snapshotListener.remove()
            }
        }

    override suspend fun createExercise(exercise: Exercise, file: File): Response<Boolean> {
        return try {
            val fromFile = Uri.fromFile(file)
            val ref = storageExerciseRef.child(file.name)
            ref.putFile(fromFile).await()
            val url = ref.downloadUrl.await()

            exercise.image = url.toString()
            exercisesRef.add(exercise).await()
            Response.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun updateExercise(exercise: Exercise, file: File?): Response<Boolean> {
        return try {
            if (file != null) {
                val fromFile = Uri.fromFile(file)
                val ref = storageExerciseRef.child(file.name)
                ref.putFile(fromFile).await()
                val url = ref.downloadUrl.await()
                exercise.image = url.toString()
            }
            val map: MutableMap<String, Any> = HashMap()
            map["name"] = exercise.name
            map["remarks"] = exercise.remarks
            map["image"] = exercise.image
            map["trainingId"] = exercise.trainingId

            exercisesRef.document(exercise.id).update(map).await()
            Response.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun deleteExercise(exerciseId: String): Response<Boolean> {
        return try {
            exercisesRef.document(exerciseId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }
}
