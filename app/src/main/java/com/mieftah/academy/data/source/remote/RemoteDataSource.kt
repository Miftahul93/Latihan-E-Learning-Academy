package com.mieftah.academy.data.source.remote

import android.os.Handler
import android.os.Looper
import com.mieftah.academy.data.source.remote.response.ContentResponse
import com.mieftah.academy.data.source.remote.response.CourseResponse
import com.mieftah.academy.data.source.remote.response.ModuleResponse
import com.mieftah.academy.utils.EspressoIdlingResource
import com.mieftah.academy.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(helper).apply { instance = this }
        }
    }

    fun getAllCourses(callback: LoadCoursesCallback) { //: List<CourseResponse> = jsonHelper.loadCourses()
        EspressoIdlingResource.increment()
        handler.postDelayed(
            {
                callback.onAllCoursesReceived(jsonHelper.loadCourses())
                EspressoIdlingResource.decrement()
            }, SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getModules(
        courseId: String,
        callback: LoadModuleCallback
    ) {// : List<ModuleResponse> = jsonHelper.loadModule(courseId)
        EspressoIdlingResource.increment()
        handler.postDelayed(
            {
                callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getContent(
        moduleId: String,
        callback: LoadContentCallback
    ) {//: ContentResponse = jsonHelper.loadContent(moduleId)
        EspressoIdlingResource.increment()
        handler.postDelayed(
            {
                callback.onContentReceived(jsonHelper.loadContent(moduleId))
                EspressoIdlingResource.decrement()
            },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponse: List<CourseResponse>)
    }

    interface LoadModuleCallback {
        fun onAllModulesReceived(moduleResponse: List<ModuleResponse>)
    }

    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }

}