package cn.today.architecture.base.repository

open class BaseRepositoryBoth<R : IRemoteDataSource, L : ILocalDataSource>(
    val remoteDataSource: R,
    val localDataSource: L
) : IRepository

open class BaseRepositoryLocal<L : ILocalDataSource>(val localDataSource: L) : IRepository

open class BaseRepositoryRemote<R : IRemoteDataSource>(val remoteDataSource: R) : IRepository

open class BaseRepositoryNothing() : IRepository