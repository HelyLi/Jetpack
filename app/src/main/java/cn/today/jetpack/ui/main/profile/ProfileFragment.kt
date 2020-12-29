package cn.today.jetpack.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.today.architecture.base.view.fragment.BaseFragment
import cn.today.architecture.ext.observe
import cn.today.architecture.image.GlideApp
import cn.today.jetpack.R
import cn.today.jetpack.utils.toast
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private val mViewModel: ProfileViewModel by viewModels()

    override val layoutId: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        observe(mViewModel.viewStateLiveData, this::onNewState)
        mBtnEdit.setOnClickListener { toast { "coming soon ..." } }
    }

    private fun onNewState(state: ProfileViewState) {
        if (state.throwable != null) {

        }
        if (state.userInfo != null) {
            GlideApp.with(requireContext())
                .load(state.userInfo.avatarUrl)
                .apply(RequestOptions().circleCrop())
                .into(mIvAvatar)

            mTvNickname.text = state.userInfo.name
            mTvBio.text = state.userInfo.bio
            mTvLocation.text = state.userInfo.location
        }
    }
}