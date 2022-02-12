package com.mieftah.academy.di

import android.content.Context
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.data.source.local.LocalDataSource
import com.mieftah.academy.data.source.local.room.AcademyDatabase
import com.mieftah.academy.data.source.remote.RemoteDataSource
import com.mieftah.academy.utils.AppExecutors
import com.mieftah.academy.utils.JsonHelper

object Injection {
    fun providerRepository(context: Context) : AcademyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}