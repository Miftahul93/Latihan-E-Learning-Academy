package com.mieftah.academy.ui.academy

import com.mieftah.academy.data.CourseEntity
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
class AcademyViewModelTest {

    private lateinit var viewModel: AcademyViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository)
    }

    @Test
    fun getCourse() {
        `when` (academyRepository.getAllCourse()).thenReturn(DataDummy.generateDummyCourses() as ArrayList<CourseEntity>)
        val courseEntities = viewModel.getCourse()
        verify<AcademyRepository>(academyRepository).getAllCourse()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
    }
}