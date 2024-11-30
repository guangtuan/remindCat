package tech.igrant.filedb.retrofitdriven

import java.lang.reflect.Proxy

class FileDrivenDb(private val requestHandler: RequestHandler) {

    fun <T> createService(t: Class<T>): T {
        return t.cast(
            Proxy.newProxyInstance(
                t.classLoader,
                arrayOf(t)
            ) { _, method, args ->
                val req = RetrofitExt.parse(method, args)
                requestHandler.handleForResp(req)
            }
        )!!
    }

}
