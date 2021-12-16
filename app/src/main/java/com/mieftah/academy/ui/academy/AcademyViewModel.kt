package com.mieftah.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.utils.DataDummy

class AcademyViewModel : ViewModel() {
    fun getCourse(): List<CourseEntity> = DataDummy.generateDummyCourses()
}