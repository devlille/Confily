package com.paligot.confily.core.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual val httpClientEngine: HttpClientEngine
    get() = Js.create()