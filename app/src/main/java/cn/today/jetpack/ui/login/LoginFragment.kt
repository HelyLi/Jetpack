package cn.today.jetpack.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.today.architecture.base.view.fragment.BaseFragment
import cn.today.jetpack.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_login

    private val mViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mBtnSignIn.setOnClickListener {
            mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
        }
    }
}