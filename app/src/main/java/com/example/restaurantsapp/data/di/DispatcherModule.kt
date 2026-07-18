package com.example.restaurantsapp.data.di

import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import kotlinx.coroutines.*
import javax.inject.*

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher
            = Dispatchers.Main

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}