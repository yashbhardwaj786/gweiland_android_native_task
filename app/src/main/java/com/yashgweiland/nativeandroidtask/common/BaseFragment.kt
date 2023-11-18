package com.yashgweiland.nativeandroidtask.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.yashgweiland.nativeandroidtask.notifiers.Loader
import com.yashgweiland.nativeandroidtask.notifiers.Notify
import com.yashgweiland.nativeandroidtask.notifiers.NotifyException
import com.yashgweiland.nativeandroidtask.notifiers.NotifyRetry
import com.yashgweiland.nativeandroidtask.utils.Utility
import kotlinx.coroutines.ExperimentalCoroutinesApi

abstract class BaseFragment : Fragment() {
    private lateinit var baseBinding: ViewDataBinding

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity = context as BaseActivity
        if (!dataBinding) {
            inflater.inflate(layoutResource, container, false)
        } else {
            baseBinding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
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
                            Utility.showProgressDialog(getActivity() as BaseActivity, true)
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
        return baseBinding.root
    }

    fun getBinding(): ViewDataBinding {
        return baseBinding
    }
    abstract val dataBinding: Boolean
    abstract val layoutResource: Int
    abstract fun getViewModel(): BaseViewModel?
    abstract fun onNotificationReceived(data: Notify)
    abstract fun setBindings()

    inline fun <reified T> lazyBinding(): Lazy<T> = lazy { getBinding() as T }
}