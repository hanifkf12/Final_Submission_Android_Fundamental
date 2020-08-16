package com.hanifkf12.finalsubmissionfundamental.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.adapter.FavoriteAdapter
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {
    companion object{
        const val EXTRA_DATA_DB = "DATA_DB"
    }
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter : FavoriteAdapter
    val data : MutableList<DetailUserResponse> = mutableListOf()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter = FavoriteAdapter(data)
        adapter.setOnItemClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA_DB, it)
            startActivity(intent)
        }
        rv_favorites.adapter = adapter
        rv_favorites.layoutManager = LinearLayoutManager(context)
        rv_favorites.setHasFixedSize(true)
        favoriteViewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar2.visibility = if(it) View.VISIBLE else View.GONE
        })
        favoriteViewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isNullOrEmpty()){
                    hideData()
                    showEmpty()
                }else{
                    hideEmpty()
                    showData()
                    data.clear()
                    data.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        favoriteViewModel.getFavoritesUser()


    }

    private fun hideData(){
        rv_favorites.visibility = View.GONE
    }

    private fun showData(){
        rv_favorites.visibility = View.VISIBLE
    }

    private fun hideEmpty(){
        iv_empty_favorite.visibility = View.GONE
    }

    private fun showEmpty(){
        iv_empty_favorite.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoritesUser()
    }
}
