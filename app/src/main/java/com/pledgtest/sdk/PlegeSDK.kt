package com.pledgtest.sdk

import com.pledgtest.model.Transaction

interface PlegeSDK{
    val viewModel: PlegeSDKViewModel
    fun startTransaction(transaction: Transaction)
}