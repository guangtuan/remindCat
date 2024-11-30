package tech.igrant.filedb.retrofitdriven

import tech.igrant.fundation.gson.JSON
import java.io.File
import java.lang.reflect.Proxy
import java.nio.charset.StandardCharsets
import java.util.UUID

class FileDrivenDb(
    private val root: File
) {

    fun <T> createService(t: Class<T>): T {
        return t.cast(
            Proxy.newProxyInstance(
                t.classLoader,
                arrayOf(t)
            ) { _, method, args ->
                work(RetrofitExt.parse(method, args))
            }
        )!!
    }

    private fun work(request: Request): Any {
        return when (request) {
            is Get -> {
                when (request.resultType) {
                    is ListT -> {
                        File(root, request.path).listFiles()
                            ?.map { JSON.de(it.readText(), request.resultType.raw) }
                            ?: emptyList<Any>()
                    }

                    else -> {
                        TODO("not impl")
                    }
                }
            }

            is Post -> {
                val namespace = File(root, request.path)
                val name = UUID.randomUUID().toString()
                File(namespace, name).writeText(JSON.se(request.body), StandardCharsets.UTF_8)
                when (request.resultType) {
                    is Normal -> {
                        return request.body
                    }

                    else -> {
                        TODO("not impl")
                    }
                }
            }

            else -> {
                TODO("not impl")
            }
        }
    }
}
