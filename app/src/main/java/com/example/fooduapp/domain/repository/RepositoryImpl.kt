package com.example.fooduapp.domain.repository

import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.model.Restaurant
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class RepositoryImpl @Inject constructor(@Named private val refFoods: CollectionReference,
                                         @Named private val refRestaurants: CollectionReference) : Repository {
    override suspend fun deleteFood(food: Food): DataResponse<Boolean> {
        return try {
            refFoods.document(food.id).delete()
            DataResponse.Success(true)
        } catch (e: Exception) {
            DataResponse.Failure(e)
        }
    }

    override suspend fun insertFood(food: Food): DataResponse<Boolean> {
        return try {
            val id: String = refFoods.document().id
            food.id = id
            refFoods.document(id).set(food).await()
            DataResponse.Success(true)
        } catch (e: Exception) {
            DataResponse.Failure(e)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getFoods(): Flow<DataResponse<List<Food>>> = callbackFlow {
        val snapListener = refFoods.addSnapshotListener{ value, error ->
            GlobalScope.launch(Dispatchers.IO) {
                val response = if (value != null) {
                    val foods = value.toObjects(Food::class.java)
                    DataResponse.Success(foods)
                } else {
                    DataResponse.Failure(error)
                }
                trySend(response)
            }
        }
        awaitClose{ snapListener.remove() }
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun getRestaurants(): Flow<DataResponse<List<Restaurant>>> = callbackFlow {
        val snapListener = refRestaurants.addSnapshotListener { value, error ->
            GlobalScope.launch(Dispatchers.IO) {
                val response = if (value != null) {
                    val restaurants = value.toObjects(Restaurant::class.java)
                    DataResponse.Success(restaurants)
                } else {
                    DataResponse.Failure(error)
                }
                trySend(response)
            }
        }
        awaitClose { snapListener.remove() }
    }
}