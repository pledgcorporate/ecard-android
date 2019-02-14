package com.pledgtest.model

import com.squareup.moshi.Json

class Payload {
    @Json(name = "virtualCard")
    lateinit var virtualCard: VirtualCard

    override fun toString(): String {
        return "Payload(virtualCard=$virtualCard)"
    }

}
