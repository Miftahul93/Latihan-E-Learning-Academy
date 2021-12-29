package com.mieftah.academy.ui.bookmark

import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks() : List<CourseEntity> = academyRepository.getBookmarkedCourses() // sebelumnya -> DataDummy.generateDummyCourses()
}