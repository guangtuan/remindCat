package tech.igrant.filedb.retrofitdriven

import java.io.File
import java.nio.charset.StandardCharsets
import java.util.UUID

class RequestHandler(private val root: File) {

    fun handleForResp(request: Request): Any {
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
                println(namespace.mkdirs())
                println(namespace)
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