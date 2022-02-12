package com.mieftah.academy.ui.reader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mieftah.academy.data.source.local.entity.ContentEntity
import com.mieftah.academy.data.source.local.entity.ModuleEntity
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
class CourseReaderViewModelTest {

    private lateinit var viewModel: CourseReaderViewModel

    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId
    private val dummyModules = DataDummy.generateDummyModules(courseId)
    private val moduleId = dummyModules[0].moduleId

    // LiveData 1
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    // LiveData 2
    @Mock
    private lateinit var modulesObserver: Observer<List<ModuleEntity>>

    @Mock
    private lateinit var moduleObserver: Observer<ModuleEntity>

    @Before
    fun setUp() {
        viewModel = CourseReaderViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
        viewModel.setSelectedModule(moduleId)

        val dummyModule = dummyModules[0]
        dummyModule.contentEntity = ContentEntity("<h3 class=\\\"fr-text-bordered\\\">" + dummyModule.title + "</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
    }

    @Test
    fun getModules() {
        // LiveData 3
        val modules = MutableLiveData<List<ModuleEntity>>()
        modules.value = dummyModules

        `when`(academyRepository.getAllModulesByCourse(courseId)).thenReturn(modules)
        val moduleEntities = viewModel.getModules().value
        verify(academyRepository).getAllModulesByCourse(courseId)
        assertNotNull(moduleEntities)
        assertEquals(7, moduleEntities?.size)

        viewModel.getModules().observeForever(modulesObserver)
        verify(modulesObserver).onChanged(dummyModules)
    }

    @Test
    fun getSelectedModule() {
        // LiveData 4
        val module = MutableLiveData<ModuleEntity>()
        module.value = dummyModules[0]

        `when`(academyRepository.getContent(courseId, moduleId)).thenReturn(module)
        val moduleEntity = viewModel.getSelectedModule().value as ModuleEntity
        verify(academyRepository).getContent(courseId, moduleId)
        assertNotNull(moduleEntity)
        val contentEntity = moduleEntity.contentEntity
        assertNotNull(contentEntity)
        val content = contentEntity?.content
        assertNotNull(content)
        assertEquals(content, dummyModules[0].contentEntity?.content)

        viewModel.getSelectedModule().observeForever(moduleObserver)
        verify(moduleObserver).onChanged(dummyModules[0])
    }
}