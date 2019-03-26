package com.pledgtest.viewmodel

import androidx.lifecycle.ViewModel
import com.pledg.sdk.model.Transaction
import com.pledg.sdk.PledgSDK

class MainActivityViewModel : ViewModel() {
    lateinit var pledgSDK: PledgSDK
    val transaction = Transaction(
        "mer_8ec99f9a-f650-4893-a4a3-16f20e16bb66",
        350,
        "EUR",
        "sales@pledge.co",
        "lukasz@lukas.com",
        "en_GB",
        "order_132",
        "STAY IN LONDON",
        "Fly + Hotel 2 nights (3 rooms)"
    )

    fun onUrlClick() {
        pledgSDK.startTransaction(transaction)
    }
}