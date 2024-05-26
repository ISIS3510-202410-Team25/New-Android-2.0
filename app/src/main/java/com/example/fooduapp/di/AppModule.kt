package com.example.fooduapp.di

import com.example.fooduapp.domain.repository.Repository
import com.example.fooduapp.domain.repository.RepositoryImpl
import com.example.fooduapp.domain.usecases.DeleteFood
import com.example.fooduapp.domain.usecases.FoodUseCase
import com.example.fooduapp.domain.usecases.GetFood
import com.example.fooduapp.domain.usecases.InsertFood
import com.example.fooduapp.domain.usecases.RestaurantUseCase
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Named("FoodsCollection")
    fun provideRefFoods(db: FirebaseFirestore): CollectionReference = db.collection("Foods")

    // Proporciona la referencia a la colección de restaurantes (Restaurants) en Firestore
    @Provides
    @Named("RestaurantsCollection")
    fun provideRefRestaurants(db: FirebaseFirestore): CollectionReference = db.collection("Restaurants")

    // Proporciona una instancia de Repository utilizando las referencias a las colecciones
    @Provides
    fun provideRepository(
        @Named("FoodsCollection") refFoods: CollectionReference,
        @Named("RestaurantsCollection") refRestaurants: CollectionReference
    ): Repository = RepositoryImpl(refFoods, refRestaurants)

    // Proporciona una instancia de RestaurantUseCase para el uso en el ViewModel de restaurantes
    @Provides
    fun provideRestaurantUseCase(repository: Repository) = RestaurantUseCase(repository)

    // Proporciona una instancia de FoodUseCase para el uso en el ViewModel de alimentos
    @Provides
    fun provideFoodUseCase(repository: Repository) = FoodUseCase(
        getFood = GetFood(repository),
        insertFood = InsertFood(repository),
        deleteFood = DeleteFood(repository)
    )
}