package com.example.github.ui.userslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.databinding.FragmentUsersListBinding
import com.example.github.model.UserListModel
import com.example.github.network.ViewState
import com.example.github.ui.ErrorDialog
import com.example.github.ui.Loading
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : Fragment(), UsersListAdapter.SelectUserOnClickListener {

    private val viewModel: UsersListViewModel by viewModel()
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersListAdapter
    private var querySearch: String = ""

    private val loading: Loading by lazy { Loading(this.requireActivity()) }
    private val errorDialog: ErrorDialog by lazy { ErrorDialog(this.requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupRecyclerView()
        loadUsersList()
        setupSearch()
    }

    private fun setupSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return loadSearchList(query)
            }

            override fun onQueryTextChange(query: String): Boolean {
                return loadSearchList(query)
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = UsersListAdapter(this)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun loadUsersList() {
        viewModel.getAllUsers()
    }

    private fun setupObservables() {
        viewModel.usersList.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Error -> {
                    errorDialog.show()
                    loading.dismiss()
                }
                is ViewState.Success -> {
                    val users = it.data
                    adapter.setData(users)
                    loading.dismiss()
                }
                else -> Unit
            }
        }
    }

    override fun selectUser(user: UserListModel) {
        val action = UsersListFragmentDirections.actionUsersListFragmentToUserFragment(user.login)
        findNavController().navigate(action)
    }

    private fun loadSearchList(query: String): Boolean {
        if (query.isNotEmpty()) {
            querySearch = query
            viewModel.getSearchUser(querySearch)
        } else if (query.isEmpty() || query == "") {
            viewModel.getAllUsers()
        }
        return false
    }

}