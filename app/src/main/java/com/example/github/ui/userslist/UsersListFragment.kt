package com.example.github.ui.userslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var llmanager: LinearLayoutManager

    private var flagPagination = 0
    private var list: ArrayList<UserListModel> = arrayListOf()

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
        setScrollView()
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
        llmanager = LinearLayoutManager(context)
        binding.recycler.layoutManager = llmanager
    }

    private fun loadUsersList() {
        viewModel.getAllUsers()
    }

    private fun setupObservables() {
        viewModel.usersList.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    showLoading()
                }
                is ViewState.Error -> {
                    errorDialog.show(it.errorMessage)
                    hideLoading()
                }
                is ViewState.Success -> {
                    list.addAll(it.data)
                    adapter.setData(list)
                    hideLoading()
                }
                else -> Unit
            }
        }

        viewModel.usersListSearch.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                }
                is ViewState.Error -> {
                    errorDialog.show(it.errorMessage)
                }
                is ViewState.Success -> {
                    adapter.setData(it.data)
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
            viewModel.getSearchUser(query)
        } else if (query.isEmpty() || query == "") {
            viewModel.getAllUsers()
        }
        return false
    }

    private fun setScrollView() {
        binding.recycler.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val adapItens = adapter?.itemCount
                    val lastItenVisible = llmanager.findLastVisibleItemPosition()

                    if (adapItens != null) {
                        if (lastItenVisible == adapItens - 1) {
                            viewModel.getAllUsers()
                            flagPagination++
                        }
                    }
                }
            })
        }
    }

    private fun showLoading() {
        if (flagPagination == 0) {
            loading.show()
        } else {
            binding.progressHorizontal.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.progressHorizontal.visibility = View.INVISIBLE
        loading.dismiss()
    }


}