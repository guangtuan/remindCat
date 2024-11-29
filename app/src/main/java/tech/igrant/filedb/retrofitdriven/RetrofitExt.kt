package tech.igrant.filedb.retrofitdriven

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.lang.reflect.Method

object RetrofitExt {

    private val supportMethods = listOf(
        GET::class.java,
        POST::class.java,
        PUT::class.java,
        DELETE::class.java
    )

    fun parse(method: Method): Request {
        return supportMethods
            .map { method.getAnnotation(it) }
            .firstOrNull()
            ?.let {
                when (it) {
                    is GET -> Request(HttpMethod.GET, it.value)
                    is POST -> Request(HttpMethod.POST, it.value)
                    is PUT -> Request(HttpMethod.PUT, it.value)
                    is DELETE -> Request(HttpMethod.DELETE, it.value)
                    else -> throw IllegalArgumentException("Unknown annotation")
                }
            }
            ?: throw IllegalArgumentException("Unknown annotation")
    }
}