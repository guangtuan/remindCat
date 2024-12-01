package tech.igrant.filedb.retrofitdriven

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.kotlinFunction

object RetrofitExt {

    private val supportMethods = listOf(
        GET::class.java,
        POST::class.java,
        PUT::class.java,
        DELETE::class.java
    )

    private fun typeConvert(type: Type): ResultType {
        return  when (type) {
            is ParameterizedType -> {
                when (type.rawType) {
                    java.util.List::class.java -> {
                        ListT(type.actualTypeArguments[0], type)
                    }
                    else -> {
                        ResultType.PlaceHolder.instance
                    }
                }
            }
            is Class<*> -> Normal(type, type)
            else -> ResultType.PlaceHolder.instance
        }
    }

    fun parse(method: Method, args: Array<Any>): Request {
        return supportMethods
            .firstNotNullOfOrNull { method.getAnnotation(it) }
            ?.let {
                val t = method.kotlinFunction?.returnType?.javaType ?: method.returnType
                when (it) {
                    is GET -> Get(it.value, typeConvert(t))
                    is POST -> Post(it.value, typeConvert(t), args[0])
                    is PUT -> Put(it.value, typeConvert(t))
                    is DELETE -> Delete(it.value)
                    else -> throw IllegalArgumentException("Unknown annotation")
                }
            }
            ?: throw IllegalArgumentException("Unknown annotation")
    }
}