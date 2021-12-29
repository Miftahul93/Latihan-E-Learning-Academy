package com.mieftah.academy.di

import android.content.Context
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.data.source.remote.RemoteDataSource
import com.mieftah.academy.utils.JsonHelper

object injection {
    fun providerRepository(context: Context) : AcademyRepository {
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDataSource)
    }
}