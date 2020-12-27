package cn.today.jetpack.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import cn.today.jetpack.repository.UserInfoRepository
import cn.today.protobuf.UserPreferencesSerializer
import com.today.protobuf.UserPreferencesProtos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(userDataStore: DataStore<UserPreferencesProtos.UserPreferences>): UserInfoRepository {
        return UserInfoRepository.getInstance(userDataStore)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(application: Application): DataStore<UserPreferencesProtos.UserPreferences> {
        return application.createDataStore(
                fileName = "user_prefs.pb",
                serializer = UserPreferencesSerializer
        )
    }
}