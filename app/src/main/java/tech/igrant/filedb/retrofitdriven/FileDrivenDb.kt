package tech.igrant.filedb.retrofitdriven

import tech.igrant.fundation.gson.JSON
import tech.igrant.fundation.id.Random
import java.io.File
import java.lang.reflect.Proxy
import java.nio.charset.StandardCharsets

class FileDrivenDb(
    private val root: File
) {

    private fun ensurePresent(root: File, name: String): File {
        val res = File(root, name)
        if (res.exists()) {
            return res
        }
        if (res.mkdirs()) {
            return res
        }
        throw RuntimeException("failed to create ${res.absoluteFile}")
    }

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
                        ensurePresent(root, path)
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
                val namespace = ensurePresent(root, path)
                File(namespace, Random.id()).writeText(JSON.se(body), StandardCharsets.UTF_8)
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
