package com.hanifkf12.finalsubmissionfundamental.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.model.response.Item
import kotlinx.android.synthetic.main.item_user.view.*

class MainAdapter(private val data : List<Item>) : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private lateinit var listener : (Item) -> Unit
    fun setOnItemClickListener(listener : (Item)-> Unit){
        this.listener = listener
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Item){
            Glide.with(itemView).load(item.avatarUrl).into(itemView.iv_user)
            itemView.tv_user.text = item.login
            itemView.setOnClickListener {
                listener(item)
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