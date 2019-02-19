package com.pledgtest.viewmodel

import androidx.lifecycle.ViewModel
import com.pledgtest.model.Transaction
import com.pledgtest.sdk.PlegeSDKViewModel

class MainActivityViewModel : ViewModel() {
    lateinit var plegeViewModel: PlegeSDKViewModel
    val transaction = Transaction(
        "mer_8ec99f9a-f650-4893-a4a3-16f20e16bb66",
        3500,
        "EUR",
        "sales@pledge.co",
        "lukasz@lukas.com",
        "en_GB",
        "order_123",
        "STAY IN LONDON",
        "Fly + Hotel 2 nights (3 rooms)"
    )

    fun onUrlClick() {
        plegeViewModel.startTransaction(transaction)
    }
}