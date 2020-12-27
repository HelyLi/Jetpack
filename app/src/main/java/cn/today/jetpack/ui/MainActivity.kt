package cn.today.jetpack.ui

import android.os.Bundle
import cn.today.architecture.base.view.activity.BaseActivity
import cn.today.jetpack.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}