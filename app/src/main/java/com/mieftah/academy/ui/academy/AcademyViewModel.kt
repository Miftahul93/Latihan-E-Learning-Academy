package com.mieftah.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.data.source.AcademyRepository
import com.mieftah.academy.vo.Resource

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourse(): LiveData<Resource<PagedList<CourseEntity>>> = academyRepository.getAllCourse()
}