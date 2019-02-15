package com.pledgtest.model

import com.squareup.moshi.Json
class Response {
    @Json(name = "name")
    var name: String? = null
    @Json(name = "payload")
    var payload: Payload? = null

    override fun toString(): String {
        return "Response(name=$name, payload=$payload)"
    }

}
