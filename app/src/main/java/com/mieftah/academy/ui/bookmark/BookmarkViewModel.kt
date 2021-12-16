package com.mieftah.academy.ui.bookmark

import androidx.lifecycle.ViewModel
import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.utils.DataDummy

class BookmarkViewModel : ViewModel() {
    fun getBookmarks() : List<CourseEntity> = DataDummy.generateDummyCourses()
}