package powerrangers.eivom.domain.utility

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

fun <T> Resource<List<T>>.addList(resource: Resource<List<T>>): Resource<List<T>> {
    return try {
        val list = (this.data ?: emptyList()) + resource.data!!
        Resource.Success(data = list)
    }
    catch (e: Exception) {
        Resource.Error(
            data = this.data,
            message = e.message ?: resource.message ?: ResourceErrorMessage.ADD_LIST
        )
    }
}

fun <T> Resource<T>.toLoading(data: T? = null): Resource.Loading<T> {
    return if (data != null)
        Resource.Loading(data = data)
    else
        Resource.Loading(data = this.data)
}

fun <T> Resource<T>.toError(data: T? = null, message: String): Resource.Error<T> {
    return if (data != null)
        Resource.Error(data = data, message = message)
    else
        Resource.Error(data = this.data, message = message)
}

object ResourceErrorMessage {
    const val UNKNOWN = "Unknown error"

    const val ADD_LIST = "Adding Resource<List> error"

    const val CONVERT_MOVIELIST_TO_MOVIELISTITEMS = "Converting Resource<MovieList> to Resource<MovieListItems> error"
    const val CONVERT_LOCALMOVIEITEMS_TO_MOVIELISTITEMS = "Converting Resource<LocalMovieItems> to Resource<MovieListItems> error"
    const val CONVERT_MOVIEINFORMATION_TO_MOVIEITEM = "Converting Resource<MovieInformation> to Resource<MovieItem> error"

    const val GET_MOVIELIST = "Getting Resource<MOVIELIST> error"
    const val GET_MOVIEINFORMATION = "Getting Resource<MOVIEINFORMATION> error"
    const val GET_MOVIEVIDEO = "Getting Resource<MOVIEVIDEO> error"
    const val GET_MOVIEIMAGE = "Getting Resource<MOVIEIMAGE> error"

    const val MOVIELIST_END = "End of movie list error"
    const val LOAD_MOVIELIST = "Loading movie list error"

    const val GET_LOCALMOVIELIST = "Getting Resource<LOCALMOVIELIST> error"
    const val GET_LOCALMOVIEITEM = "Getting Resource<LOCALMOVIEITEM> error"
    const val GET_LOCALMOVIEMAP = "Getting Resource<LOCALMOVIEMAP> error"

    const val SIGN_IN = "Sign in error"
    const val SIGN_IN_WITH_INTENT = "Sign in with intent error"
    const val GET_SIGNED_IN_USER = "Get signed in user error"
}