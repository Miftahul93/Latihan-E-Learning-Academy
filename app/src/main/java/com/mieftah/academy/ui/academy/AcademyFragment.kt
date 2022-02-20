package com.mieftah.academy.ui.academy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mieftah.academy.databinding.FragmentAcademyBinding
import com.mieftah.academy.utils.DataDummy
import com.mieftah.academy.viewmodel.ViewModelFactory
import com.mieftah.academy.vo.Status

class AcademyFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentAcademyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentAcademyBinding = FragmentAcademyBinding.inflate(layoutInflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]

            val academyAdapter = AcademyAdapter()
            viewModel.getCourse().observe(viewLifecycleOwner, { courses ->
                if (courses != null) {
                    when(courses.status) {
                        Status.LOADING -> fragmentAcademyBinding?.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentAcademyBinding?.progressBar?.visibility = View.GONE
                            academyAdapter.submitList(courses.data)
                        }
                        Status.ERROR -> {
                            fragmentAcademyBinding?.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentAcademyBinding?.rvAcademy) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = academyAdapter
            }
            //langkah 16
/*
            // Menerapkan Live Data dalam jetpak
            fragmentAcademyBinding.progressBar.visibility = View.VISIBLE
            viewModel.getCourse().observe(this@, {courses ->
                fragmentAcademyBinding.progressBar.visibility = View.GONE
                academyAdapter.setCourses(courses)
                academyAdapter.notifyDataSetChanged()
            })

            with(fragmentAcademyBinding.rvAcademy) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = academyAdapter
            }*/
        }
    }
}