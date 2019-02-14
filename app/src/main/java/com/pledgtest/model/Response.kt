package com.pledgtest.model

import com.squareup.moshi.Json

class Response {
    @Json(name = "name")
    lateinit var name: String
    @Json(name = "payload")
    lateinit var payload: Payload

    override fun toString(): String {
        return "Response(name=$name, payload=$payload)"
    }

}
