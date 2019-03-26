package com.pledg.sdk

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.pledg.sdk.model.Response

interface PledgSDKViewModel : Observable {
    val onActiveTransaction: LiveData<Boolean>
    val onPurchaseSuccess: LiveData<Response>
    val onPurchaseError: LiveData<Boolean>
    val onScrollTopEvent: LiveData<Boolean>
    val onCloseEvent: LiveData<Boolean>
}