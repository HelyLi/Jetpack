package cn.today.jetpack.ui.login

import cn.today.architecture.base.repository.BaseRepositoryBoth
import cn.today.architecture.base.repository.ILocalDataSource
import cn.today.architecture.base.repository.IRemoteDataSource
import cn.today.jetpack.base.Results
import cn.today.jetpack.db.UserDatabase
import cn.today.jetpack.entity.UserInfo
import cn.today.jetpack.http.service.ServiceManager
import cn.today.jetpack.manager.UserManager
import cn.today.jetpack.repository.UserInfoRepository
import cn.today.jetpack.utils.processApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepository @Inject constructor(
    remoteDataSource: LoginRemoteDataSource,
    localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(
    remoteDataSource,
    localDataSource
) {

    suspend fun login(username: String, password: String): Results<UserInfo> {
        localDataSource.savePrefUser(username, password)
        val userInfo = remoteDataSource.login()

        // 如果登录失败，清除登录信息
        when (userInfo) {
            is Results.Failure -> localDataSource.clearPrefsUser()
            is Results.Success -> UserManager.INSTANCE = requireNotNull(userInfo.data)
        }
        return userInfo
    }

    fun fetchAutoLogin(): Flow<AutoLoginEvent> {
        return localDataSource.fetchAutoLogin()
    }
}

class LoginRemoteDataSource @Inject constructor(private val serviceManager: ServiceManager) :
    IRemoteDataSource {
    suspend fun login(): Results<UserInfo> {
        return processApiResponse { serviceManager.userService.fetchUserOwner() }
    }
}

class LoginLocalDataSource @Inject constructor(
    private val db: UserDatabase,
    private val userRepository: UserInfoRepository
) : ILocalDataSource {

    suspend fun savePrefUser(username: String, password: String) {
        userRepository.saveUserInfo(username, password)
    }

    suspend fun clearPrefsUser() {
        userRepository.saveUserInfo("", "")
    }

    fun fetchAutoLogin(): Flow<AutoLoginEvent> {
        return userRepository.fetchUserInfoFlow()
            .map { user ->
                val username = user.username
                val password = user.password
                val isAutoLogin = user.autoLogin
                when (username.isNotEmpty() && password.isNotEmpty() && isAutoLogin) {
                    true -> AutoLoginEvent(true, username, password)
                    false -> AutoLoginEvent(false, "", "")
                }
            }
    }
}

data class AutoLoginEvent(
    val autoLogin: Boolean,
    val username: String,
    val password: String
)