package com.mieftah.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks() : LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourses() // sebelumnya -> DataDummy.generateDummyCourses()
}