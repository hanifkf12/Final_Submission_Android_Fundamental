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
import com.hanifkf12.finalsubmissionfundamental.adapter.FollowingAdapter
import com.hanifkf12.finalsubmissionfundamental.model.response.FollowingResponseItem
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: FollowingAdapter
    private val data: MutableList<FollowingResponseItem> = mutableListOf()

    companion object {
        fun newInstance(username: String?): FollowingFragment {
            val bundle = Bundle()
            bundle.putString("username", username)
            val followingFragment = FollowingFragment()
            followingFragment.arguments = bundle
            return followingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString("username", null)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        adapter = FollowingAdapter(data)
        adapter.setOnItemClickListener {

        }
        rv_following.adapter = adapter
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.setHasFixedSize(true)
        viewModel.following.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNullOrEmpty()) {
                    rv_following.visibility = View.GONE
                    tv_empty_following.visibility = View.VISIBLE
                } else {
                    data.clear()
                    data.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar4.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.getUserFollowing(username!!)
    }

}
