package com.mieftah.academy.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mieftah.academy.R
import com.mieftah.academy.data.source.local.entity.CourseEntity
import com.mieftah.academy.databinding.FragmentBookmarkBinding
import com.mieftah.academy.viewmodel.ViewModelFactory


class BookmarkFragment : Fragment(), BookmarkAdapter.BookmarkFragmentCallback {

    lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    private lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(fragmentBookmarkBinding?.rvBookmark)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]

            adapter = BookmarkAdapter(this)
            fragmentBookmarkBinding?.progressBar?.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(viewLifecycleOwner, { courses ->
                fragmentBookmarkBinding.progressBar.visibility = View.GONE
                adapter.submitList(courses)
            })

            with(fragmentBookmarkBinding.rvBookmark) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            // Aksi di bawah digunakan untuk melakukan swap ke kenan dan ke kiri
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                // Sebelum melakukan penghapusan, course harus mendapatkan posisi dari item yang di swipe
                val swipedPosition = viewHolder.adapterPosition
                // Kemudian memanggil CourseEntity sesuai posisi ketika diswipe
                val courseEntity = adapter.getSwipedData(swipedPosition)
                // Melakukan setBookmark untuk menghapus bookmark dari list course
                courseEntity?.let { viewModel.setBookmark(it) }

                // Memanggil Snackbar untuk melakukan pengecekan, apakah benar melakukan penghapusan bookmark
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                // Mengembalikan item yang terhapus
                snackbar.setAction(R.string.message_ok) { v ->
                    courseEntity?.let { viewModel.setBookmark(it) }
                }
                // Menampilkan snackbar
                snackbar.show()
            }
        }
    })

    override fun onShareClick(course: CourseEntity) {
    }
}