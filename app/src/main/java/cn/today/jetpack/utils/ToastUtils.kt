package cn.today.jetpack.utils

import cn.today.architecture.ext.toast
import cn.today.jetpack.base.BaseApplication

inline fun toast(value: () -> String): Unit = BaseApplication.INSTANCE.toast(value)