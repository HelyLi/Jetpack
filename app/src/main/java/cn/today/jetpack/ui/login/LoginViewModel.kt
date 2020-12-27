package cn.today.jetpack.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cn.today.architecture.base.viewmodel.BaseViewModel
import cn.today.architecture.ext.postNext
import cn.today.jetpack.base.Results
import cn.today.jetpack.http.Errors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@Suppress("checkResult")
class LoginViewModel @ViewModelInject constructor(private val repo: LoginRepository) :
    BaseViewModel() {

    private val _stateLiveData: MutableLiveData<LoginViewState> =
        MutableLiveData(LoginViewState.initial())
    private val autoLoginInfoFlow: Flow<AutoLoginEvent> = repo.fetchAutoLogin().take(1)

    val autoLoginLiveData = autoLoginInfoFlow.asLiveData()
    val stateLiveData: LiveData<LoginViewState> = _stateLiveData

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(
                    isLoading = false,
                    throwable = Errors.EmptyInputError,
                    loginInfo = null
                )
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, throwable = null, loginInfo = null)
                }
                when (val result = repo.login(username, password)) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = result.error, loginInfo = null)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = null, loginInfo = result.data)
                    }
                }
            }
        }
    }
}