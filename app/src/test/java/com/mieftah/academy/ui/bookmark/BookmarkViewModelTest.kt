package com.mieftah.academy.ui.bookmark

import com.mieftah.academy.data.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        Mockito.`when`<ArrayList<CourseEntity>>(academyRepository.getBookmarkedCourses()).thenReturn(DataDummy.generateDummyCourses() as ArrayList<CourseEntity>)
        val courseEntities = viewModel.getBookmarks()
        Mockito.verify<AcademyRepository>(academyRepository).getBookmarkedCourses()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
    }
}