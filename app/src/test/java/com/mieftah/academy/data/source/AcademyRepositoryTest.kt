package com.mieftah.academy.data.source

import com.mieftah.academy.data.source.remote.RemoteDataSource
import com.mieftah.academy.data.source.remote.response.ContentResponse
import com.mieftah.academy.data.source.remote.response.CourseResponse
import com.mieftah.academy.data.source.remote.response.ModuleResponse
import com.mieftah.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class AcademyRepositoryTest {

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourse()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].modulId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourse() {
        `when`<List<CourseResponse>>(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntities = academyRepository.getAllCourse()
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    } //langkah 12

    @Test
    fun getBookmarkedCourses() {
        `when`<List<CourseResponse>>(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntites = academyRepository.getBookmarkedCourses()
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(courseEntites)
        assertEquals(courseResponses.size.toLong(), courseEntites.size.toLong())
    }

    @Test
    fun getCourseWithModules() {
        `when`<List<CourseResponse>>(remote.getAllCourses()).thenReturn(courseResponses)
        val resultCourse = academyRepository.getCourseWithModules(courseId)
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(resultCourse)
        assertEquals(courseResponses[0].title, resultCourse.title)
    }

    @Test
    fun getAllModulesByCourse() {
        `when`<List<ModuleResponse>>(remote.getModules(courseId)).thenReturn(moduleResponses)
        val moduleEntites = academyRepository.getAllModulesByCourse(courseId)
        verify<RemoteDataSource>(remote).getModules(courseId)
        assertNotNull(moduleEntites)
        assertEquals(moduleResponses.size.toLong(), moduleEntites.size.toLong())
    }

    @Test
    fun getContent() {
        `when`<List<ModuleResponse>>(remote.getModules(courseId)).thenReturn(moduleResponses)
        `when`<ContentResponse>(remote.getContent(moduleId)).thenReturn(content)
        val resultModule = academyRepository.getContent(courseId, moduleId)
        verify<RemoteDataSource>(remote).getContent(moduleId)
        assertNotNull(resultModule)
        assertEquals(content.content, resultModule.contentEntity?.content)
    }
}