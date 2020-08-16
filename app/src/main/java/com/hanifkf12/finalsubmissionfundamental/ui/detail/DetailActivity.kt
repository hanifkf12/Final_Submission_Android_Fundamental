package com.hanifkf12.finalsubmissionfundamental.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.adapter.DetailPagerAdapter
import com.hanifkf12.finalsubmissionfundamental.model.response.DetailUserResponse
import com.hanifkf12.finalsubmissionfundamental.model.response.Item
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA_ITEM = "DATA_ITEM"
        const val EXTRA_DATA_DB = "DATA_DB"

    }
    private lateinit var adapter: DetailPagerAdapter
    private lateinit var viewModel: DetailViewModel
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = R.drawable.bg_transparant
        }
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.container)
        ) { _, insets ->
            Log.d("INSETS", insets.systemWindowInsetTop.toString())
            iv_back.setMargin(insets.systemWindowInsetTop + 50, insets.systemWindowInsetLeft + 50)
            iv_favorite.setNewMargin(insets.systemWindowInsetTop + 50, insets.systemWindowInsetRight + 50)
            insets.consumeSystemWindowInsets()
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }
        val intent = intent
        val item = intent.getParcelableExtra<Item>(EXTRA_DATA_ITEM)
        val detailUserResponse = intent.getParcelableExtra<DetailUserResponse>(EXTRA_DATA_DB)
        val username = if(item!=null) item.login else detailUserResponse?.login
        val id = if(item!=null) item.id else detailUserResponse?.id
        adapter = DetailPagerAdapter(supportFragmentManager, this, username)
        viewPagerFollow.adapter = adapter
        tabFollow.setupWithViewPager(viewPagerFollow)


        viewModel.getDetailUser(username!!)
        if (id != null) {
            viewModel.getSingleUser(id)
        }
        viewModel.dataDb.observe(this, Observer {
            it?.let {
                isFavorite = !isFavorite
                favoriteState()
            }
        })
        viewModel.status.observe(this, Observer {
            Toast.makeText(this, it,Toast.LENGTH_SHORT).show()
        })
        viewModel.data.observe(this, Observer {
            it.also {
                Glide.with(this).load(it.avatarUrl).into(iv_avatar)
                tv_name.text = it.name
                tv_login.text = it.login
                tv_followers.text = it.followers.toString()
                tv_following.text = it.following.toString()
                tv_repository.text = it.publicRepos.toString()
                iv_favorite.setOnClickListener {_->
                    if(!isFavorite) viewModel.addFavorite(it) else viewModel.removeFavorite(it)
                    isFavorite = !isFavorite
                    favoriteState()
                }
            }
        })

    }
    private fun View.setMargin(marginTop: Int, marginLeft: Int) {
        val viewLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        viewLayoutParams.topMargin = marginTop
        viewLayoutParams.leftMargin = marginLeft
    }

    private fun View.setNewMargin(marginTop: Int, marginRight: Int) {
        val viewLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        viewLayoutParams.topMargin = marginTop
        viewLayoutParams.rightMargin = marginRight
    }

    private fun favoriteState() {
        if (isFavorite){
            iv_favorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp))
        }else{
            iv_favorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp))
        }
    }
}
