package powerrangers.eivom.feature_movie.ui.movie_list

data class FilterState(
    val isTrending: Boolean = false,
    val isTrendingDay: Boolean = false,
    val isTrendingWeek: Boolean = false,
    val isFavorite: Boolean = false,
    val isWatched: Boolean = false,
    val isUpdated: Boolean = false,
    val Region: String? = null,
    val AdultContentIncluded: Boolean = false,
    val ReleaseYear: Int? = null,
    val MinimumReleaseDate: String? = null,
    val MaximumReleaseDate: String? = null,
    val MinimumRating: Float? = null,
    val MaximumRating: Float? = null,
    val MinimumVote: Int? = null,
    val MaximumVote: Int? = null,
    val Genre: String? = null,
    val OriginCountry: String? = null,
    val OriginLanguage: String? = null,
    val MinimumLength: Int? = null,
    val MaximumLength: Int? = null,
    val WithoutGenre: String? = null,

)