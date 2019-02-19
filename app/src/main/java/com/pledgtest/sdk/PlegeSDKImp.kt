package com.pledgtest.sdk

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.webkit.*

class PlegeSDKImp(val activit: Activity) : PlegeSDK {
    private val TAG = "PlegeSDK"
    override val viewModel: PlegeSDKViewModelImp = PlegeSDKViewModelImp()
    private val postMessage = PostMessage()
    private lateinit var web: WebView
    private var handler: Handler = Handler(activit.mainLooper)

    override fun settingWebView(web: WebView) {
        this.web = web
        web.addJavascriptInterface(postMessage, "webkit")
        web.setDownloadListener { _, _, _, _, _ ->
        }
        web.webChromeClient = object : WebChromeClient() {}
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (request!!.url.toString().endsWith("downloadCGU-en")) {
                    return startWebPageWithPdf(request)
                }
                view!!.loadUrl(request!!.url.toString())
                return true
            }
        }
        val settings = web.settings
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true

        web.loadUrl(viewModel.generateUrl())
    }

    private fun startWebPageWithPdf(request: WebResourceRequest?): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, request!!.url)
        activit.startActivity(intent)
        return true
    }

    inner class PostMessage {
        @JavascriptInterface
        fun postMessage(value: String?) {
            handler.post {
                Log.d(TAG, "postMessage $value")
                if (value != null) {
                    viewModel.onMessage(value)
                }
            }
        }

    }
}