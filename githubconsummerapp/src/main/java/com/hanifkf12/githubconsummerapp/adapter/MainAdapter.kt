package com.hanifkf12.githubconsummerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanifkf12.githubconsummerapp.R
import com.hanifkf12.githubconsummerapp.model.User
import kotlinx.android.synthetic.main.item_favorites.view.*

class MainAdapter(private val data : List<User>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private lateinit var listener : (User) -> Unit
    fun setOnItemClickListener(listener : (User)-> Unit){
        this.listener = listener
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: User){
            Glide.with(itemView).load(user.avatarUrl).into(itemView.iv_user_favorite)
            itemView.tv_user_favorite.text = user.name
            itemView.tv_login_favorite.text = user.login
            itemView.setOnClickListener {
                listener(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorites,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

}