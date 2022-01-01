package com.mieftah.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mieftah.academy.data.ContentEntity
import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.data.ModuleEntity
import com.mieftah.academy.data.source.remote.RemoteDataSource
import com.mieftah.academy.data.source.remote.response.ContentResponse
import com.mieftah.academy.data.source.remote.response.CourseResponse
import com.mieftah.academy.data.source.remote.response.ModuleResponse

class FakeAcademyRepository (private val remoteDataSource: RemoteDataSource) : AcademyDataSource {

}