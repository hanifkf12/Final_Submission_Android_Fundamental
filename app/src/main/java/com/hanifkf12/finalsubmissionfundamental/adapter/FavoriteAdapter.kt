package com.hanifkf12.finalsubmissionfundamental.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import kotlinx.android.synthetic.main.item_favorites.view.*

class FavoriteAdapter(private val data : List<DetailUserResponse>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private lateinit var listener : (DetailUserResponse) -> Unit
    fun setOnItemClickListener(listener : (DetailUserResponse)-> Unit){
        this.listener = listener
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: DetailUserResponse){
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