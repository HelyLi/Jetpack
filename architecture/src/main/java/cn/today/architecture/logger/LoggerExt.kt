package cn.today.architecture.logger

import android.app.Application
import cn.today.architecture.functional.Supplier
import timber.log.Timber


fun Application.initLogger(debug: Boolean = true) {
    if (debug)
        Timber.plant(Timber.DebugTree())
    else
        Timber.plant(CrashReportingTree())

    log { "initLogger successfully, debug = $debug" }
}

inline fun log(supplier: Supplier<String>) = logd(supplier)

inline fun logd(supplier: Supplier<String>) = Timber.d(supplier())

inline fun logi(supplier: Supplier<String>) = Timber.i(supplier())

inline fun logw(supplier: Supplier<String>) = Timber.w(supplier())

inline fun loge(supplier: Supplier<String>) = Timber.e(supplier())