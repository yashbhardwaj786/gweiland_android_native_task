package com.yashgweiland.nativeandroidtask.common

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yashgweiland.nativeandroidtask.R
import com.yashgweiland.nativeandroidtask.notifiers.Loader
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.notifiers.NotifyException
import com.yashgweiland.nativeandroidtask.notifiers.NotifyRetry
import com.yashgweiland.nativeandroidtask.utils.Utility

abstract class BaseActivity  : AppCompatActivity() {
    private lateinit var baseBinding: ViewDataBinding
    private var toolbar: Toolbar? = null
    var title: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setTitle(" ")
        if (!dataBinding) {
            setContentView(layoutResource)
        } else {
            baseBinding = DataBindingUtil.setContentView(this, layoutResource)
        }

        setBindings()
        getViewModel()?.let {
            it.notifier.receive { event ->
                when (event) {
                    is NotifyException -> {
                        event.exception.message?.let { msg ->
                        }
                    }
                    is Loader -> {
                        if (event.loading) {
                            Utility.showProgressDialog(this)
                        } else {
                            Utility.hideProgressDialog()
                        }
                    }
                    is NotifyRetry -> {
                    }
                    else -> {
                        onNotificationReceived(event)
                    }
                }
            }
        }


    }

    fun getBinding(): ViewDataBinding {
        return baseBinding
    }

    fun setToolBar(titleText: String) {
        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.title)
        title?.maxLines = 1
        title?.ellipsize = TextUtils.TruncateAt.END

            title?.text = titleText

        toolbar?.contentInsetStartWithNavigation = 0
        setSupportActionBar(toolbar)

        toolbar?.setNavigationOnClickListener { finish() }

        toolbar?.navigationIcon?.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    this,
                    R.color.white
                ), BlendModeCompat.SRC_ATOP
            )
    }

    abstract val dataBinding: Boolean
    abstract val layoutResource: Int
    abstract fun getViewModel(): BaseViewModel?
    abstract fun onNotificationReceived(data: Notify)
    abstract fun setBindings()

    inline fun <reified T> lazyBinding(): Lazy<T> = lazy { getBinding() as T }

}