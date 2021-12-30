package com.mieftah.academy.ui.detail

import com.mieftah.academy.data.ModuleEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {

    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(dummyCourse)
        //viewModel.setSelectedCourse(dummyCourse.courseId)
        val courseActivity = viewModel.getCourse()
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(courseActivity)
        assertEquals(dummyCourse.courseId, courseActivity.courseId)
        assertEquals(dummyCourse.deadline, courseActivity.deadline)
        assertEquals(dummyCourse.description, courseActivity.description)
        assertEquals(dummyCourse.imagePath, courseActivity.imagePath)
        assertEquals(dummyCourse.title, courseActivity.title)
    }

    @Test
    fun getModules() {
        `when`<ArrayList<ModuleEntity>>(academyRepository.getAllModulesByCourse(courseId)).thenReturn(DataDummy.generateDummyModules(courseId))
        val moduleEntities = viewModel.getModules()
        verify<AcademyRepository>(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities.size.toLong())
    }
}