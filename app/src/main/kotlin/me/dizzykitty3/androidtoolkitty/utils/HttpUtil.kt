package me.dizzykitty3.androidtoolkitty.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
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
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject

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

    suspend fun get(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse =
        try {
            withContext(Dispatchers.IO) {
                client.get(url) {
                    headers.forEach { (key, value) ->
                        header(key, value)
                    }
                    params.forEach { (key, value) ->
                        parameter(key, value)
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
            throw e
        }

    suspend fun post(
        url: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse = try {
        withContext(Dispatchers.IO) {
            client.post(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    } catch (e: Exception) {
        handleException(e)
        throw e
    }

    suspend fun put(
        url: String,
        body: Any,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse = try {
        withContext(Dispatchers.IO) {
            client.put(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    } catch (e: Exception) {
        handleException(e)
        throw e
    }

    suspend fun delete(
        url: String,
        params: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse = try {
        withContext(Dispatchers.IO) {
            client.delete(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                params.forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }
    } catch (e: Exception) {
        handleException(e)
        throw e
    }

    private suspend fun handleException(exception: Exception) {
        if (exception is ResponseException) {
            val errorBody = exception.response.bodyAsText()

            try {
                val jsonObj = JSONObject(errorBody)
                val errorMessage = jsonObj.getString("message")
                ToastUtil.toast(errorMessage)
            } catch (e: JSONException) {
                ToastUtil.toast("Error parsing error message: ${e.localizedMessage}")
            }
        } else {
            ToastUtil.toast("An unexpected error occurred: ${exception.localizedMessage}")
        }
    }
}