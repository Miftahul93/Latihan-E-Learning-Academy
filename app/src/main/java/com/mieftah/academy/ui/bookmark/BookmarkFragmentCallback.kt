package com.mieftah.academy.ui.bookmark

import com.mieftah.academy.data.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}