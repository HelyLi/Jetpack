package cn.today.jetpack.ui.login

import android.os.Bundle
import cn.today.architecture.base.view.activity.BaseActivity
import cn.today.jetpack.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override val layoutId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    
    private fun initFragment() {

    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}