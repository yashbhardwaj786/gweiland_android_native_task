package com.yashgweiland.nativeandroidtask.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)