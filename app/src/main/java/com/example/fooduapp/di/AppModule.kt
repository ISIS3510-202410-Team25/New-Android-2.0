package com.example.fooduapp.di

import com.example.fooduapp.domain.repository.FoodRepository
import com.example.fooduapp.domain.repository.FoodRepositoryImpl
import com.example.fooduapp.domain.usecases.DeleteFood
import com.example.fooduapp.domain.usecases.FoodUseCase
import com.example.fooduapp.domain.usecases.GetFood
import com.example.fooduapp.domain.usecases.InsertFood
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideRefFoods(db: FirebaseFirestore): CollectionReference = db.collection("Foods")

    @Provides
    fun provideRepository(impl: FoodRepositoryImpl): FoodRepository = impl

    @Provides
    fun provideFoodUseCase(foodRepository: FoodRepository) = FoodUseCase(
        getFood = GetFood(foodRepository),
        insertFood = InsertFood(foodRepository),
        deleteFood = DeleteFood(foodRepository)
    )
}