package tech.igrant.filedb.retrofitdriven

import java.lang.reflect.Type

interface ResultType {
    class PlaceHolder : ResultType {
        companion object {
            val instance = PlaceHolder()
        }
    }
}

class ListT(val eleType: Type, val raw: Type) : ResultType {
    override fun toString(): String {
        return raw.toString()
    }
}

class Normal(val clazz: Class<*>, val raw: Type) : ResultType {
    override fun toString(): String {
        return raw.toString()
    }
}
