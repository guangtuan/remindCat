package tech.igrant.fundation.id

import java.util.UUID

object Random {

    fun id(): String {
        return UUID.randomUUID().toString()
    }

}