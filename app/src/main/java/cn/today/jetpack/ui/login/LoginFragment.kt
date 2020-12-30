package cn.today.jetpack.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import cn.today.architecture.base.view.fragment.BaseFragment
import cn.today.architecture.ext.observe
import cn.today.jetpack.R
import cn.today.jetpack.http.Errors
import cn.today.jetpack.ui.MainActivity
import cn.today.jetpack.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.HttpException

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_login

    private val mViewModel: LoginViewModel by viewModels()

    private val permissionRequest: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGrant ->
            when (isGrant) {
                true -> mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
                false -> Toast.makeText(
                    requireContext(),
                    "need permission first.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mBtnSignIn.setOnClickListener {
            println("username:" + tvUsername.text.toString() + ",password:" + tvPassword.text.toString())
            mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
        }

        observe(mViewModel.stateLiveData, this::onNewState)
        observe(mViewModel.autoLoginLiveData, this::onAutoLogin)
    }

    private fun onAutoLogin(autoLoginEvent: AutoLoginEvent) {
        if (autoLoginEvent.autoLogin) {
            tvUsername.setText(autoLoginEvent.username, TextView.BufferType.EDITABLE)
            tvPassword.setText(autoLoginEvent.password, TextView.BufferType.EDITABLE)

            mViewModel.login(autoLoginEvent.username, autoLoginEvent.password)
        }
    }

    private fun onNewState(state: LoginViewState) {
        if (state.throwable != null) {
            when (state.throwable) {
                is Errors.EmptyInputError -> "username or password can't be null."
                is HttpException -> when (state.throwable.code()) {
                    401 -> "username or password failure."
                    else -> "network failure"
                }
                else -> "network failure"
            }.also { str -> toast { str } }
        }

        mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.loginInfo != null) {
            MainActivity.launch(requireActivity())
        }
    }
}