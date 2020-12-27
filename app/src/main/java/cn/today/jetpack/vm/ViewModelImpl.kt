package cn.today.jetpack.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelImpl constructor() : ViewModel() {

    private lateinit var liveData: MutableLiveData<String>;

    fun ld() {

//        liveData = MutableLiveData<String>()
//        liveData.observe(context, Observer<String> {
//
//        })
//
//        liveData.value = ""
//
//        liveData.postValue("")
    }

}