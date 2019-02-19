package com.pledgtest.sdk

import android.webkit.WebView

interface PlegeSDK{
    val viewModel: PlegeSDKViewModel
    fun settingWebView(view :WebView)
}