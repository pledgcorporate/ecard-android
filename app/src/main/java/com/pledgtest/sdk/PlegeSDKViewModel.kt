package com.pledgtest.sdk

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.pledgtest.model.Response

interface PlegeSDKViewModel : Observable {
    val onActiveTransaction: LiveData<Boolean>
    val onPurchaseSuccess: LiveData<Response>
    val onPurchaseError: LiveData<Boolean>
    val onScrollTopEvent: LiveData<Boolean>
    val onCloseEvent: LiveData<Boolean>
}