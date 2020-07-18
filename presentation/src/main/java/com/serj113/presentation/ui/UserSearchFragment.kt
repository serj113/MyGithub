package com.serj113.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.serj113.presentation.databinding.UserSearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class UserSearchFragment : Fragment() {

    private lateinit var binding: UserSearchFragmentBinding
    private lateinit var adapter: UserListAdapter

    private val viewModel: UserSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserSearchFragmentBinding.inflate(inflater, container, false)
        adapter = UserListAdapter()

        binding.recyclerview.adapter = adapter
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.queryEditText.addTextChangedListener { query ->
            query?.let {
                viewModel.queryChannel.offer(it.toString())
            }
        }

        viewModel.searchPagedListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}