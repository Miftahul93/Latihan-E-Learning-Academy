package com.mieftah.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mieftah.academy.data.source.local.entity.ModuleEntity
import com.mieftah.academy.databinding.FragmentModuleContentBinding
import com.mieftah.academy.ui.reader.CourseReaderViewModel
import com.mieftah.academy.viewmodel.ViewModelFactory
import com.mieftah.academy.vo.Status

class ModuleContentFragment : Fragment() {

    private lateinit var viewModel: CourseReaderViewModel

    companion object {
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    private lateinit var fragmentModuleContentBinding: FragmentModuleContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentModuleContentBinding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return fragmentModuleContentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            viewModel.selectedModule.observe(viewLifecycleOwner, { moduleEntity ->
                if (moduleEntity != null) {
                    when (moduleEntity.status) {
                        Status.LOADING -> fragmentModuleContentBinding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            fragmentModuleContentBinding?.progressBar?.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }
                           setButtonNextPrevState(moduleEntity.data)
                            if (!moduleEntity.data.read) {
                                viewModel.readContent(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            fragmentModuleContentBinding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }

                    fragmentModuleContentBinding?.btnNext?.setOnClickListener { viewModel.setNextPage() }
                    fragmentModuleContentBinding?.btnPrev?.setOnClickListener { viewModel.setPrevpage() }
                }
            })
        }
    }

    private fun setButtonNextPrevState(module: ModuleEntity) {
        if (activity != null) {
            when(module.position) {
                0 -> {
                    fragmentModuleContentBinding?.btnPrev?.isEnabled = false
                    fragmentModuleContentBinding?.btnNext?.isEnabled = true
                }
                viewModel.getModuleSize() - 1 -> {
                    fragmentModuleContentBinding?.btnPrev?.isEnabled = true
                    fragmentModuleContentBinding?.btnNext?.isEnabled = false
                }
                else -> {
                    fragmentModuleContentBinding?.btnPrev?.isEnabled = true
                    fragmentModuleContentBinding?.btnNext?.isEnabled = true
                }
            }
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        fragmentModuleContentBinding.webView.loadData(module.contentEntity?.content ?: "", "text/html", "UTF-8")
    }
}