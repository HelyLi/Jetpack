package cn.today.jetpack.ui.main.repos

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import cn.today.architecture.base.view.fragment.BaseFragment
import cn.today.architecture.ext.jumpBrowser
import cn.today.architecture.ext.observe
import cn.today.jetpack.R
import cn.today.jetpack.utils.removeAllAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*

@AndroidEntryPoint
class ReposFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_profile

    private val mViewModel: ReposViewModel by viewModels()

    private val mAdapter = ReposPagedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_repos_filter_type)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.removeAllAnimation()

        binds()
    }

    private fun binds() {
        mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }

        fabTop.setOnClickListener {
            mRecyclerView.scrollToPosition(0)
        }

        toolbar.setOnMenuItemClickListener {
            onMenuSelected(it)
            true
        }

        observe(mAdapter.getItemClickEvent(), requireActivity()::jumpBrowser)

        observe(mAdapter.loadStateFlow.asLiveData()) { loadStates ->
            mSwipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        observe(mViewModel.pagedListLiveData) {
            mAdapter.submitData(lifecycle, it)
            mRecyclerView.scrollToPosition(0)
        }
    }

    private fun onMenuSelected(menuItem: MenuItem) {
        val isKeyUpdated = mViewModel.setSortKey(
                when (menuItem.itemId) {
                    R.id.menu_repos_letter -> ReposViewModel.sortByLetter
                    R.id.menu_repos_update -> ReposViewModel.sortByUpdate
                    R.id.menu_repos_created -> ReposViewModel.sortByCreated
                    else -> throw IllegalArgumentException("failure menuItem id.")
                }
        )
        if (isKeyUpdated)
            mAdapter.refresh()
    }
}