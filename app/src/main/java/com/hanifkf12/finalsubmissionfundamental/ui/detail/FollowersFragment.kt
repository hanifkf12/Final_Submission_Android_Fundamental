package com.hanifkf12.finalsubmissionfundamental.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.adapter.FollowerAdapter
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowersResponseItem
import kotlinx.android.synthetic.main.fragment_followers.*

/**
 * A simple [Fragment] subclass.
 */
class FollowersFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter : FollowerAdapter
    private val data : MutableList<FollowersResponseItem> = mutableListOf()
    companion object{
        fun newInstance(username: String?) : FollowersFragment{
            val bundle = Bundle()
            bundle.putString("username", username)
            val followersFragment = FollowersFragment()
            followersFragment.arguments = bundle
            return followersFragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString("username",null)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        adapter = FollowerAdapter(data)
        adapter.setOnItemClickListener {

        }
        rv_followers.adapter = adapter
        rv_followers.layoutManager = LinearLayoutManager(context)
        rv_followers.setHasFixedSize(true)
        viewModel.followers.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isNullOrEmpty()){
                    rv_followers.visibility = View.GONE
                    tv_empty_followers.visibility = View.VISIBLE
                }else{
                    data.clear()
                    data.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar3.visibility = if (it) View.VISIBLE else View.GONE
         })

        viewModel.getUserFollowers(username!!)
    }
}
