package me.dizzykitty3.androidtoolkitty.data.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber

object HttpUtil {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        defaultRequest {
            accept(ContentType.Application.Json)
        }
    }

    suspend fun get(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse = withContext(Dispatchers.IO) {
        client.get(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
            params.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }

    suspend fun post(
        url: String, body: Any, headers: Map<String, String> = emptyMap()
    ): HttpResponse = withContext(Dispatchers.IO) {
        client.post(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    suspend fun put(
        url: String, body: Any, headers: Map<String, String> = emptyMap()
    ): HttpResponse = withContext(Dispatchers.IO) {
        client.put(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    suspend fun delete(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse = withContext(Dispatchers.IO) {
        client.delete(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
            params.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}