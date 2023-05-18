package com.example.github.ui.userslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.model.UserListModel

class UsersListAdapter(private val selectUserListener: SelectUserOnClickListener) :
    RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

    private var usersList: List<UserListModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.userLogin.text = currentUser.login
        if (!currentUser.avatar_url.isNullOrBlank()) {
            Glide.with(holder.userAvatar)
                .load(currentUser.avatar_url)
                .circleCrop()
                .into(holder.userAvatar)
        }

        holder.itemView.setOnClickListener {
            selectUserListener.selectUser(currentUser)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(usersListFromApi: List<UserListModel>) {
        this.usersList = usersListFromApi
        notifyDataSetChanged()
    }


    interface SelectUserOnClickListener {
        fun selectUser(user: UserListModel)
    }

    inner class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var userAvatar: ImageView = item.findViewById(R.id.user_avatar_image)
        var userLogin: TextView = item.findViewById(R.id.user_login)

    }

}