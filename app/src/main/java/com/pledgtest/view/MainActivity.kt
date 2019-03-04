package com.pledgtest.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pledg.sdk.model.Response
import com.pledgtest.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.pledgtest.R
import com.pledgtest.databinding.ActivityMainBinding
import com.pledg.sdk.PledgSDK
import com.pledg.sdk.PledgSDKViewModel
import com.pledg.sdk.createPlegSDK


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var pledgSDK: PledgSDK

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                viewModel = this@MainActivity.viewModel
                setLifecycleOwner(this@MainActivity)
            }
        settingPledgDK()
    }

    private fun settingPledgDK() {
        pledgSDK = createPlegSDK(this, web)
        pledgSDK.viewModel.onActiveTransaction.observeForever {
            it?.let {
                listenToTransaction(pledgSDK.viewModel)
            }
        }
        viewModel.pledgSDK = pledgSDK
    }

    private fun listenToTransaction(viewModel: PledgSDKViewModel) {
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