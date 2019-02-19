package com.pledgtest.sdk

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.pledgtest.model.Response
import com.pledgtest.model.Transaction

interface PlegeSDKViewModel : Observable {
    fun startTransaction(transaction: Transaction)
    val onActiveTransaction: LiveData<Boolean>
    val onPurchaseSuccess: LiveData<Response>
    val onPurchaseError: LiveData<Boolean>
    val onScrollTopEvent: LiveData<Boolean>
    val onCloseEvent: LiveData<Boolean>
}