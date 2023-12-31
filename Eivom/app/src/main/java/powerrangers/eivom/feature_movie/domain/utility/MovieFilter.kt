package powerrangers.eivom.feature_movie.domain.utility

import java.time.LocalDate

sealed class MovieFilter {
    class Trending(val trendingTime: TrendingTime): MovieFilter()   // No additional filter, sort

    class Favorite(val isFavorite: Boolean): MovieFilter()  // No region filter
    class Watched(val isWatched: Boolean): MovieFilter() // No region filter

    class Region(val region: String): MovieFilter() // No favorite and watched filter
    class AdultContentIncluded(val isIncluded: Boolean): MovieFilter()
//    class Language(value: String): MovieFilter(value) Translate to corresponding language
    class ReleaseYear(val year: Int): MovieFilter()
    class MinimumReleaseDate(val releaseDate: LocalDate): MovieFilter()
    class MaximumReleaseDate(val releaseDate: LocalDate): MovieFilter()
    class MinimumRating(val rating: Float): MovieFilter()
    class MaximumRating(val rating: Float): MovieFilter()
    class MinimumVote(val voteCount: Int): MovieFilter()
    class MaximumVote(val voteCount: Int): MovieFilter()

    class Genre(val genres: List<Int>, val logic: Logic): MovieFilter()
    class OriginCountry(val countries: List<String>, val logic: Logic): MovieFilter()
    class OriginLanguage(val languages: List<String>, val logic: Logic): MovieFilter()
//    class ReleaseType
    class MinimumLength(val movieLength: Int): MovieFilter()
    class MaximumLength(val movieLength: Int): MovieFilter()

//    class WithoutCompany(value: List<String>): MovieFilter()
    class WithoutGenre(val withoutGenres: List<Int>, val logic: Logic): MovieFilter()
}

enum class TrendingTime {
    DAY, WEEK
}

enum class Logic {
    AND, OR
}