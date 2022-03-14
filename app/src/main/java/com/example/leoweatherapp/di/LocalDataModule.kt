package com.example.leoweatherapp.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName.plus("_preferences"), Context.MODE_PRIVATE)
}