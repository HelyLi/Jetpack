package cn.today.jetpack.base

import android.app.Application
import cn.today.architecture.logger.initLogger
import cn.today.jetpack.BuildConfig
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initLogger(BuildConfig.DEBUG)
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}