package com.hanifkf12.finalsubmissionfundamental.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.adapter.MainAdapter
import com.hanifkf12.finalsubmissionfundamental.model.response.Item
import com.hanifkf12.finalsubmissionfundamental.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    companion object{
        const val EXTRA_DATA_ITEM = "DATA_ITEM"
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter : MainAdapter
    private val data : MutableList<Item> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = MainAdapter(data)
        adapter.setOnItemClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA_ITEM, it)

            startActivity(intent)
        }
        rv_github_users.adapter = adapter
        rv_github_users.layoutManager = LinearLayoutManager(context)
        rv_github_users.setHasFixedSize(true)
        homeViewModel.users.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                hideEmpty()
                showData()
                data.clear()
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                hideData()
                showEmpty()
            }

        })
        homeViewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        et_search.addTextChangedListener {
            if (it.toString().isNotEmpty()){
                homeViewModel.getUsersGithub(it?.trim().toString())
            }else{
                hideData()
                showEmpty()
            }
        }
    }
    private fun hideEmpty(){
        iv_empty.visibility = View.GONE
    }
    private fun showEmpty(){
        iv_empty.visibility = View.VISIBLE
    }
    private fun showData(){
        rv_github_users.visibility = View.VISIBLE
    }
    private fun hideData(){
        rv_github_users.visibility = View.GONE
    }
}
