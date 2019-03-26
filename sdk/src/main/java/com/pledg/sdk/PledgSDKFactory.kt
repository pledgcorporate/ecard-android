package com.pledg.sdk

import android.app.Activity
import android.webkit.WebView

fun createPlegSDK(activity: Activity, web: WebView): PledgSDK =
    PledgSDKImp(activity,web)