package com.example.github.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.model.UserRepositoriesModel


class UserRepositoriesAdapter :
    RecyclerView.Adapter<UserRepositoriesAdapter.RepositoryViewHolder>() {

    var list: List<UserRepositoriesModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_repository, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RepositoryViewHolder,
        position: Int
    ) {
        val currentRepo = list[position]
        holder.repoName.text = currentRepo.repoFullName
        holder.repoDescription.text = currentRepo.description
        holder.repoUrl.text = currentRepo.repoUrl
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(repoList: List<UserRepositoriesModel>) {
        this.list = repoList
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var repoName: TextView = item.findViewById(R.id.repo_full_name)
        var repoDescription: TextView = item.findViewById(R.id.repo_description)
        var repoUrl: TextView = item.findViewById(R.id.repo_url)
    }

}