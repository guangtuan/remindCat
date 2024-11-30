package tech.igrant.filedb.retrofitdriven

interface Request

data class Get(
    val path: String,
    val resultType: ResultType
) : Request {

}

data class Post(
    val path: String,
    val resultType: ResultType,
    val body: Any
): Request {

}

data class Put(
    val path: String,
    val resultType: ResultType
): Request {

}

data class Delete(
    val path: String
): Request {

}