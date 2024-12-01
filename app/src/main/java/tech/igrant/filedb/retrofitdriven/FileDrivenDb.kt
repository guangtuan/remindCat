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
                val (path, resultType) = request
                when (resultType) {
                    is ListT -> {
                        File(root, path)
                            .listFiles()
                            ?.map { JSON.de(it.readText(), resultType.eleType) }
                            ?: emptyList<Any>()
                    }

                    else -> {
                        TODO("not impl")
                    }
                }
            }

            is Post -> {
                val (path, resultType, body) = request
                val namespace = File(root, path)
                namespace.mkdirs()
                val name = UUID.randomUUID().toString()
                File(namespace, name).writeText(JSON.se(body), StandardCharsets.UTF_8)
                when (resultType) {
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
