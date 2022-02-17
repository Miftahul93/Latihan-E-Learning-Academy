package com.mieftah.academy.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mieftah.academy.data.source.local.LocalDataSource
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.data.source.local.entity.CourseWithModule
import com.mieftah.academy.data.source.local.entity.ModuleEntity
import com.mieftah.academy.data.source.remote.RemoteDataSource
import com.mieftah.academy.utils.AppExecutors
import com.mieftah.academy.utils.DataDummy
import com.mieftah.academy.utils.LiveDataTestUtil
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.*

class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val academyRepository = FakeAcademyRepository(remote, local, appExecutors)

    private val courseResponses = DataDummy.generateRemoteDummyCourse()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].modulId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourse() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = DataDummy.generateDummyCourses()
        `when`(local.getAllCourses()).thenReturn(dummyCourses)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourse())
        verify(local).getAllCourses()
        assertNotNull(courseEntities.data)
        assertEquals(courseResponses.size.toLong(), courseEntities.data?.size?.toLong())
    }

    @Test
    fun getBookmarkedCourses() {
        val dummyCourse = MutableLiveData<List<CourseEntity>>()
        dummyCourse.value = DataDummy.generateDummyCourses()
        `when`(local.getBookmarkedCourses()).thenReturn(dummyCourse)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())
        verify(local).getBookmarkedCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getCourseWithModules() {
        val dummyEntity = MutableLiveData<CourseWithModule>()
        dummyEntity.value = DataDummy.generateDummyCourseWithModules(DataDummy.generateDummyCourses()[0], false)
        `when`(local.getCourseWithModules(courseId)).thenReturn(dummyEntity)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
        verify(local).getCourseWithModules(courseId)
        assertNotNull(courseEntities.data)
        assertNotNull(courseEntities.data?.mCourse?.title)
        assertEquals(courseResponses[0].title, courseEntities.data?.mCourse?.title)
    }

    @Test
    fun getAllModulesByCourse() {
       val dummyModules = MutableLiveData<List<ModuleEntity>>()
        dummyModules.value = DataDummy.generateDummyModules(courseId)
        `when`(local.getAllmodulesByCourse(courseId)).thenReturn(dummyModules)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))
        verify(local).getAllmodulesByCourse(courseId)
        assertNotNull(courseEntities.data)
        assertEquals(moduleResponses.size.toLong(), courseEntities.data?.size?.toLong())
    }

    @Test
    fun getContent() {
        val dummyEntity = MutableLiveData<ModuleEntity>()
        dummyEntity.value = DataDummy.generarteDummyModuleWithContent(moduleId)
        `when`(local.getModuleWithContent(courseId)).thenReturn(dummyEntity)

        val courseEntitiesContent =
            LiveDataTestUtil.getValue(academyRepository.getContent(courseId))

        verify(local).getModuleWithContent(courseId)

        assertNotNull(courseEntitiesContent)
        assertNotNull(courseEntitiesContent.data?.contentEntity)
        assertNotNull(courseEntitiesContent.data?.contentEntity?.content)
        assertEquals(content.content, courseEntitiesContent.data?.contentEntity?.content)
    }
}