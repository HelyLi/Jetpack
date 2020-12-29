package cn.today.jetpack.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import cn.today.architecture.adapter.SimpleViewPagerAdapter
import cn.today.architecture.base.view.fragment.BaseFragment
import cn.today.jetpack.R
import cn.today.jetpack.ui.main.home.HomeFragment
import cn.today.jetpack.ui.main.profile.ProfileFragment
import cn.today.jetpack.ui.main.repos.ReposFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@Suppress("PLUGIN_WARNING")
@SuppressLint("CheckResult")
@AndroidEntryPoint
class MainFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_main

    private val mViewModle: MainViewModel by viewModels()

    private var isPortMode: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPortMode = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        viewPager.adapter = SimpleViewPagerAdapter(childFragmentManager,
                listOf(HomeFragment(), ReposFragment(), ProfileFragment()))
        viewPager.offscreenPageLimit = 2

        when (isPortMode) {
            true -> bindsPortScreen()
            false -> bindsLandScreen()
        }
    }

    private fun bindsPortScreen() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) = onPageSelectChanged(position)

            override fun onPageScrollStateChanged(state: Int) = Unit
        })

        navigation.setOnNavigationItemSelectedListener { menuItem ->
            onBottomNavigationSelectChanged(menuItem)
            true
        }
    }

    private fun onPageSelectChanged(index: Int) {
        if (isPortMode) {
            for (position in 0..index) {
                if (navigation.visibility == View.VISIBLE)
                    navigation.menu.getItem(position).isChecked = index == position
            }
        } else {
            if (viewPager.currentItem != index) {
                viewPager.currentItem = index
                if (fabMenu != null && fabMenu.isExpanded)
                    fabMenu.toggle()
            }
        }
    }

    private fun bindsLandScreen() {
        fabHome.setOnClickListener { onPageSelectChanged(0) }
        fabRepo.setOnClickListener { onPageSelectChanged(1) }
        fabProfile.setOnClickListener { onPageSelectChanged(2) }
    }

    // port-mode only
    private fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                viewPager.currentItem = 2
            }
        }
    }
    
}