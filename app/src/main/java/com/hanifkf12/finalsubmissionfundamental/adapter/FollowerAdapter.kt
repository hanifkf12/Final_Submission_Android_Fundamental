package com.hanifkf12.finalsubmissionfundamental.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowersResponseItem
import kotlinx.android.synthetic.main.item_user.view.*

class FollowerAdapter(private val data : List<FollowersResponseItem>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){

    private lateinit var listener : (FollowersResponseItem) -> Unit
    fun setOnItemClickListener(listener : (FollowersResponseItem)-> Unit){
        this.listener = listener
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: FollowersResponseItem){
            Glide.with(itemView).load(user.avatarUrl).into(itemView.iv_user)
            itemView.tv_user.text = user.login
            itemView.setOnClickListener {
                listener(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

}