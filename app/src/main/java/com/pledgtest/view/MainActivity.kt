package com.pledgtest.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pledgtest.model.Response
import com.pledgtest.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.pledgtest.R
import com.pledgtest.databinding.ActivityMainBinding
import com.pledgtest.sdk.PlegeSDK
import com.pledgtest.sdk.PlegeSDKImp
import com.pledgtest.sdk.PlegeSDKViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var plegeSDK: PlegeSDK

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plegeSDK = PlegeSDKImp(this)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        plegeSDK.viewModel.onActiveTransaction.observeForever {
            it?.let {
                plegeSDK.settingWebView(web)
                listenToTransaction(plegeSDK.viewModel)
            }
        }
        viewModel.plegeViewModel = plegeSDK.viewModel

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                viewModel = this@MainActivity.viewModel
                setLifecycleOwner(this@MainActivity)
            }
    }

    private fun listenToTransaction(viewModel: PlegeSDKViewModel) {
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
        viewModel.onCloseEvent.observeForever {
            finish()
        }
    }


    private fun showSuccessDialog(it: Response) {
        it.payload?.let {
            AlertDialog.Builder(this)
                .setTitle(R.string.purchase_success)
                .setMessage(
                    "uid: ${it.virtualCard.uid}\n" +
                            "purchase.uid:${it.virtualCard.purchase.uid}\n" +
                            "account.uid:${it.virtualCard.account.uid}\n" +
                            "state:${it.virtualCard.state}\n" +
                            "ammount:${it.virtualCard.amount}\n" +
                            "cardNumber:${it.virtualCard.cardNumber}"
                )
                .setNeutralButton(android.R.string.ok) { _, _ -> finish() }
                .create()
                .show()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.purchase_error)
            .create()
            .show()
    }
}