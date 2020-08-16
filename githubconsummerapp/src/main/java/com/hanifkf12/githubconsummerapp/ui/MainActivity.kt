package com.hanifkf12.githubconsummerapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifkf12.githubconsummerapp.R
import com.hanifkf12.githubconsummerapp.adapter.MainAdapter
import com.hanifkf12.githubconsummerapp.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : MainAdapter
    private val data : MutableList<User> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = MainAdapter(data)
        adapter.setOnItemClickListener {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }
        rv_favorites.adapter = adapter
        rv_favorites.layoutManager = LinearLayoutManager(this)
        rv_favorites.setHasFixedSize(true)
        viewModel.users.observe(this, Observer {
            it?.let {
                if(it.isNullOrEmpty()){
                    iv_empty.visibility = View.VISIBLE
                    rv_favorites.visibility = View.GONE
                }else{
                    iv_empty.visibility = View.GONE
                    rv_favorites.visibility = View.VISIBLE
                    data.clear()
                    data.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

        })
        viewModel.getUsers()
    }



    override fun onResume() {
        super.onResume()
        viewModel.getUsers()
    }
}
