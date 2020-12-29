package cn.today.jetpack.ui.main.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cn.today.architecture.base.viewmodel.BaseViewModel
import cn.today.jetpack.entity.ReceivedEvent

@SuppressWarnings("checkResult")
class HomeViewModel @ViewModelInject constructor(
    repository: HomeRepository
) : BaseViewModel() {
    val eventListLiveData: LiveData<PagingData<ReceivedEvent>> =
        repository.fetchPager().flow.cachedIn(viewModelScope).asLiveData()
}