package com.example.codingchallengeimgur.ui.home.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingchallengeimgur.R
import com.example.codingchallengeimgur.databinding.FragmentGalleryBinding
import com.example.codingchallengeimgur.domain.response.ImageData
import com.example.codingchallengeimgur.ui.common.extension.observeNetworkCall
import com.example.codingchallengeimgur.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val imageAdapter = GalleryAdapter(listener = ::onItemClick)
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewListener()
    }

    private fun setUpViewListener() {
        binding.displayOptions.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.gridOption) {
                binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            } else {
                binding.imageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            imageAdapter.notifyDataSetChanged()
        }

        viewModel.imageResponse.flowWithLifecycle(lifecycle)
            .onEach {
                requireActivity().observeNetworkCall(it)
                when (it) {
                    is Resource.Failure -> { Timber.d("Resource is Failure")}
                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        imageAdapter.submitList(it.data.data)
                    }
                }
            }.catch { Timber.e(it) }.launchIn(lifecycleScope)

        binding.imageRecyclerView.apply {
            adapter = imageAdapter
        }


        binding.searchEditText.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setQueryText(query)
                viewModel.getImages()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun onItemClick(response: ImageData) {
        //On Item Click for particular position
    }


}