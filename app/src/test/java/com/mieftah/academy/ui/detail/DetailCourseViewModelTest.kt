package com.mieftah.academy.ui.detail

import com.mieftah.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DetailCourseViewModelTest {

    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel()
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        viewModel.setSelectedCourse(dummyCourse.courseId)
        val courseActivity = viewModel.getCourse()
        assertNotNull(courseActivity)
        assertEquals(dummyCourse.courseId, courseActivity.courseId)
        assertEquals(dummyCourse.deadline, courseActivity.deadline)
        assertEquals(dummyCourse.description, courseActivity.description)
        assertEquals(dummyCourse.imagePath, courseActivity.imagePath)
        assertEquals(dummyCourse.title, courseActivity.title)
    }

    @Test
    fun getModules() {
        val moduleEntities = viewModel.getModules()
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities.size.toLong())
    }
}