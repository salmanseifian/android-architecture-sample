package com.salmanseifian.android.architecture.sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.salmanseifian.android.architecture.sample.R
import com.salmanseifian.android.architecture.sample.databinding.FragmentRepositoriesBinding
import com.salmanseifian.android.architecture.sample.presentation.RepositoriesViewModel
import com.salmanseifian.android.architecture.sample.ui.adapter.RepositoriesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {

    private val viewModel: RepositoriesViewModel by viewModels()
    private lateinit var binding: FragmentRepositoriesBinding
    private val repositoriesAdapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentRepositoriesBinding.bind(view)

        binding.rvRepositories.apply {
            this.adapter = repositoriesAdapter
            layoutManager = LinearLayoutManager(context)
        }


        viewModel.allRepositories.observe(viewLifecycleOwner, {
            it?.let { pagingData ->
                repositoriesAdapter.submitData(lifecycle, pagingData)
            }
        })
    }
}