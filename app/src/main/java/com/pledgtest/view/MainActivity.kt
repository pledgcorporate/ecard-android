package com.pledgtest.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pledgtest.R
import com.pledgtest.databinding.ActivityMainBinding
import com.pledgtest.model.Response
import com.pledgtest.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val TAG = "MainActivity"

    private val postMessage = PostMessage()
    lateinit var handler: Handler

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(mainLooper)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.transaction.observeForever {
            if (it)
                startTransaction()
        }

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                viewModel = this@MainActivity.viewModel
                setLifecycleOwner(this@MainActivity)
            }
    }

    private fun startTransaction() {
        web.addJavascriptInterface(postMessage, "webkit")
        web.setDownloadListener{ _, _, _, _, _ ->
        }
        web.webChromeClient = object : WebChromeClient() {}
        val settings = web.settings
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true

        web.loadUrl(viewModel.generateUrl())
        viewModel.onPurchaseSuccess.observeForever {
            showSuccessDialog(it)
        }
        viewModel.onPurchaseError.observeForever {
            if (it)
                showErrorDialog()
        }
        viewModel.onScrollTopEvent.observeForever {
            web.scrollY = 0
        }
        viewModel.onCloseEvent.observeForever{
            finish()
        }
    }


    private fun showSuccessDialog(it: Response) {
        AlertDialog.Builder(this)
            .setTitle(R.string.purchase_success)
            .setMessage(
                "uid: ${it.payload.virtualCard.uid}\n" +
                        "purchase.uid:${it.payload.virtualCard.purchase.uid}\n" +
                        "account.uid:${it.payload.virtualCard.account.uid}\n" +
                        "state:${it.payload.virtualCard.state}\n" +
                        "ammount:${it.payload.virtualCard.amount}\n" +
                        "cardNumber:${it.payload.virtualCard.cardNumber}"
            )
            .setNeutralButton(android.R.string.ok) { _, _ -> finish() }
            .create()
            .show()
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.purchase_error)
            .create()
            .show()
    }

    inner class PostMessage {
        @JavascriptInterface
        fun postMessage(value: String?) {
            handler.post {
                if (value != null) {
                    viewModel.onMessage(value)
                }
                Log.d(TAG, "postMessage $value")
            }
        }

    }

}