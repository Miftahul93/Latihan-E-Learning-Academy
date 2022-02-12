package com.mieftah.academy.ui.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest {

    private lateinit var viewModel: AcademyViewModel

    // LiveData 1
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    // LiveData 2
    @Mock
    private lateinit var observer: Observer<List<CourseEntity>>

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository)
    }

    @Test
    fun getCourse() {
        // LiveData 3
        val dummyCourses = DataDummy.generateDummyCourses()
        val courses = MutableLiveData<List<CourseEntity>>()
        courses.value = dummyCourses
        //
        `when` (academyRepository.getAllCourse()).thenReturn(courses)
        val courseEntities = viewModel.getCourse().value
        verify(academyRepository).getAllCourse()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities?.size)

        viewModel.getCourse().observeForever(observer)
        verify(observer).onChanged(dummyCourses)
    }
}