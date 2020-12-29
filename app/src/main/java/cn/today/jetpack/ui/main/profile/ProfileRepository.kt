package cn.today.jetpack.ui.main.profile

import cn.today.architecture.base.repository.BaseRepositoryRemote
import cn.today.architecture.base.repository.IRemoteDataSource
import cn.today.jetpack.http.service.ServiceManager
import javax.inject.Inject

class ProfileRepository @Inject constructor(remoteDataSource: ProfileRemoteDataSource) :
    BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

interface IRemoteProfileDataSource : IRemoteDataSource

class ProfileRemoteDataSource @Inject constructor(
    val serviceManager: ServiceManager
) : IRemoteProfileDataSource