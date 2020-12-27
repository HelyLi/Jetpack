package cn.today.architecture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SimpleViewPagerAdapter(
    fragmentManager: FragmentManager, private val fragments: List<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}