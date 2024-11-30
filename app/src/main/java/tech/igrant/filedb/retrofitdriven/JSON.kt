package tech.igrant.filedb.retrofitdriven

import com.google.gson.Gson
import java.lang.reflect.Type

class JSON {

    companion object {

        private val gson = Gson()

        fun de(json: String, type: Type): Any? {
            return gson.fromJson(json, type)
        }

        fun se(obj: Any): String {
            return gson.toJson(obj)
        }
    }

}