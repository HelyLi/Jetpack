package cn.today.jetpack.ui.main.repos

import androidx.annotation.MainThread
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.today.architecture.base.viewmodel.BaseViewModel
import cn.today.jetpack.entity.Repo

@SuppressWarnings("checkResult")
class ReposViewModel @ViewModelInject constructor(
        private val repository: ReposRepository) : BaseViewModel() {
    private val _viewStateLiveData: MutableLiveData<String> = MutableLiveData(sortByUpdate)

    val pagedListLiveData: LiveData<PagingData<Repo>>
        get() = repository.fetchRepoPager().flow.cachedIn(viewModelScope)
                .asLiveData()

    init {
        repository.sortKeyProvider = ::fetchSortKey
    }

    @MainThread
    fun setSortKey(sort: String): Boolean {
        return if (sort != fetchSortKey()) {
            _viewStateLiveData.postValue(sort)
            true
        } else {
            false
        }
    }

    @MainThread
    fun fetchSortKey(): String {
        return _viewStateLiveData.value!!
    }

    companion object {
        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"      // default

        const val sortByLetter: String = "full_name"
    }
}