package com.mieftah.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourse(): List<CourseEntity> = academyRepository.getAllCourse() // sebelumnya -> DataDummy.generateDummyCourses()
}