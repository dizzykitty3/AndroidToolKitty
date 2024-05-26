package me.dizzykitty3.androidtoolkitty.foundation.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
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
            level = LogLevel.ALL
        }
        defaultRequest {
            accept(ContentType.Application.Json)
        }
    }

    suspend fun get(url: String, params: Map<String, String> = emptyMap()): HttpResponse =
        withContext(Dispatchers.IO) {
            client.get(url) {
                params.forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }

    suspend fun post(url: String, body: Any): HttpResponse = withContext(Dispatchers.IO) {
        client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    suspend fun put(url: String, body: Any): HttpResponse = withContext(Dispatchers.IO) {
        client.put(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    suspend fun delete(url: String, params: Map<String, String> = emptyMap()): HttpResponse =
        withContext(Dispatchers.IO) {
            client.delete(url) {
                params.forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }
}