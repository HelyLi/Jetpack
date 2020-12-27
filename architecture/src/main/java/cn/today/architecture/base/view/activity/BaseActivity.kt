package cn.today.architecture.base.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.today.architecture.base.view.IView

abstract class BaseActivity : AppCompatActivity(), IView {
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}