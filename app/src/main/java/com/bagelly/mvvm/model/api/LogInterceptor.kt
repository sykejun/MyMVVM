package com.bagelly.mvvm.model.api

import com.orhanobut.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LogInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)
        val response = chain.proceed(request)
        return logResponse(response)
    }

    private fun logRequest(request: Request) {
        try {
            val url = request.url.toString()
            val headers = request.headers
            Logger.d("url : $url")
            Logger.d("method : " + request.method)
            if (headers != null && headers.size > 0) {
                Logger.d("headers : $headers")
            }
            val requestBody = request.body
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        Logger.d("params : " + bodyToString(request))
                    } else {
                        Logger.d("params : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logResponse(response: Response): Response {
        try {
            val builder = response.newBuilder()
            val clone = builder.build()
            var body = clone.body
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        val resp = body.string()
                        Logger.json(resp)
                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    } else {
                        Logger.d(
                            TAG,
                            "data : " + " maybe [file part] , too large too print , ignored!"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    private fun isText(mediaType: MediaType?): Boolean {
        return if (mediaType == null) false else "text" == mediaType.subtype || "json" == mediaType.subtype || "xml" == mediaType.subtype || "html" == mediaType.subtype || "webviewhtml" == mediaType.subtype || "x-www-form-urlencoded" == mediaType.subtype
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }

    companion object {
        const val TAG = "LogInterceptor"
    }
}