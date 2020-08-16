package com.hanifkf12.finalsubmissionfundamental.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hanifkf12.finalsubmissionfundamental.R
import com.hanifkf12.finalsubmissionfundamental.ui.detail.FollowersFragment
import com.hanifkf12.finalsubmissionfundamental.ui.detail.FollowingFragment

class DetailPagerAdapter(fragmentManager: FragmentManager, context: Context, username : String?) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val fragments = listOf(FollowersFragment.newInstance(username),FollowingFragment.newInstance(username))
    private val titles = listOf(context.getString(R.string.followers),context.getString(R.string.following))
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}