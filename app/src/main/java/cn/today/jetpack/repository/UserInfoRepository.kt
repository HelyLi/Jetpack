package cn.today.jetpack.repository

import androidx.datastore.core.DataStore
import cn.today.architecture.util.SingletonHolderSingleArg
import com.today.protobuf.UserPreferencesProtos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

@Suppress("PrivatePropertyName")
class UserInfoRepository(private val dataStore: DataStore<UserPreferencesProtos.UserPreferences>) {

    fun fetchUserInfoFlow(): Flow<UserPreferencesProtos.UserPreferences> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(UserPreferencesProtos.UserPreferences.getDefaultInstance())
            } else {
                throw it
            }
        }
    }

    suspend fun saveUserInfo(username: String, password: String) {
        dataStore.updateData { userPreferences ->
            userPreferences.toBuilder().setUsername(username)
                    .setPassword(password)
                    .setAutoLogin(username.isNotEmpty() && password.isNotEmpty()).build()
        }
    }
    
    companion object : SingletonHolderSingleArg<UserInfoRepository, DataStore<UserPreferencesProtos.UserPreferences>>(::UserInfoRepository)
}