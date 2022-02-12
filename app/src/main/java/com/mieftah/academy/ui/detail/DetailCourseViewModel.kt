package com.mieftah.academy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.data.source.local.entity.ModuleEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.data.source.local.entity.CourseWithModule
import com.mieftah.academy.vo.Resource

class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    val courseId = MutableLiveData<String>()

    fun setSelectedCourse(courseId: String) {
        this.courseId.value = courseId
    }

    var courseModule: LiveData<Resource<CourseWithModule>> = Transformations.switchMap(courseId) { mCourseId ->
        academyRepository.getCourseWithModules(mCourseId)
    }

    fun setBookmark() {
        val moduleResource = courseModule.value
        if (moduleResource != null) {
            val courseWithModule = moduleResource.data

            if (courseWithModule != null) {
                val courseEntity = courseWithModule.mCourse
                val newState = !courseEntity.bookmarked
                academyRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
    /*
    fun getCourse(): LiveData<CourseEntity> = academyRepository.getCourseWithModules(courseId) // sebelumnya -> {
        lateinit var course: CourseEntity
        val coursesEntities = DataDummy.generateDummyCourses()
        for (courseEntity in coursesEntities) {
            if (courseEntity.courseId == courseId) {
                course = courseEntity
            }
        }
        return course
    }
*/
}