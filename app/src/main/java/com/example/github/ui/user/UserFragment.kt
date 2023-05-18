package com.example.github.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.databinding.FragmentUserBinding
import com.example.github.model.UserModel
import com.example.github.network.ViewState
import com.example.github.ui.ErrorDialog
import com.example.github.ui.Loading
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModel()
    private lateinit var binding: FragmentUserBinding
    private lateinit var adapter: UserRepositoriesAdapter

    private val loading: Loading by lazy { Loading(this.requireActivity()) }
    private val errorDialog: ErrorDialog by lazy { ErrorDialog(this.requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userAvatarImage.visibility = View.INVISIBLE
        binding.textRepositories.visibility = View.INVISIBLE

        val args: UserFragmentArgs by navArgs()
        val login = args.userLogin

        viewModel.getUser(login)
        viewModel.getUserRepositories(login)

        setupObservables()
        setupRecyclerView()

    }

    private fun setupObservables() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Error -> {
                    errorDialog.show(it.errorMessage)
                    loading.dismiss()
                }
                is ViewState.Success -> {
                    binding.userAvatarImage.visibility = View.VISIBLE
                    binding.textRepositories.visibility = View.VISIBLE
                    val user = it.data
                    loadUserInfo(user)
                }
                else -> Unit
            }
        }

        viewModel.repositories.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Error -> {
                    errorDialog.show(it.errorMessage)
                    loading.dismiss()
                }
                is ViewState.Success -> {
                    val repos = it.data
                    adapter.setData(repos)
                    loading.dismiss()
                }
                else -> Unit
            }
        }
    }

    private fun loadUserInfo(user: UserModel) {
        if (user.avatar_url.isNotBlank()) {
            Glide.with(binding.userAvatarImage)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.userAvatarImage)
        } else {
            Glide.with(binding.userAvatarImage)
                .load(resources.getDrawable(R.drawable.avatar_place_holder))
                .circleCrop()
                .into(binding.userAvatarImage)
        }
        binding.userLogin.text = user.login
        binding.userName.text = user.userName
        binding.userUrl.text = user.userUrl
        binding.userCompany.text = user.company
        binding.userLocation.text = user.location
    }

    private fun setupRecyclerView() {
        adapter = UserRepositoriesAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.isNestedScrollingEnabled = false
    }
}