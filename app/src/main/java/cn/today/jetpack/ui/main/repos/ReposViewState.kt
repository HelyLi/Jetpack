package cn.today.jetpack.ui.main.repos

import androidx.paging.PagingData
import cn.today.jetpack.entity.Repo

data class ReposViewState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val pagedList: PagingData<Repo>?,
    val nextPageData: List<Repo>?,
    val sort: String
) {
    companion object {
        fun initial(): ReposViewState {
            return return ReposViewState(
                isLoading = false,
                throwable = null,
                pagedList = null,
                nextPageData = null,
                sort = ReposViewModel.sortByUpdate
            )
        }
    }
}