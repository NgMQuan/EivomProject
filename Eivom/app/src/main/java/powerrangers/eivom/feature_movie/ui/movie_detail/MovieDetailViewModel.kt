package powerrangers.eivom.feature_movie.ui.movie_detail

import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import powerrangers.eivom.domain.use_case.GoogleAuthClient
import powerrangers.eivom.feature_movie.domain.model.MovieItem
import powerrangers.eivom.feature_movie.domain.use_case.MovieDatabaseUseCase
import powerrangers.eivom.domain.use_case.UserPreferencesUseCase
import powerrangers.eivom.domain.utility.Resource
import powerrangers.eivom.ui.utility.UserPreferences
import powerrangers.eivom.navigation.Route
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val userPreferencesUseCase: UserPreferencesUseCase,
    private val movieDatabaseUseCase: MovieDatabaseUseCase,
    private val googleAuthClient: GoogleAuthClient,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var userPreferences = mutableStateOf(UserPreferences())
        private set

    val movieId: Int = checkNotNull(savedStateHandle[Route.MOVIE_DETAIL_SCREEN_MOVIE_ID])

    var movieInformation = mutableStateOf<Resource<MovieItem>>(Resource.Loading())
        private set

    var isEditing = mutableStateOf(false)
        private set

    var isFavorite = mutableStateOf(false)
        private set

    private var user = mutableStateOf(googleAuthClient.getSignedInUser())

    init {
        viewModelScope.launch {
            userPreferences.value =
                UserPreferences(
                    colorMode = userPreferencesUseCase.getColorMode(),
                    topbarBackgroundColor = userPreferencesUseCase.getTopbarBackgroundColor(),
                    sidebarBackgroundColor = userPreferencesUseCase.getSidebarBackgroundColor(),
                    screenBackgroundColor = userPreferencesUseCase.getScreenBackgroundColor(),
                    /**/movieNoteBackgroundColor = userPreferencesUseCase.getMovieNoteBackgroundColor(),
                    dialogBackgroundColor = userPreferencesUseCase.getDialogBackgroundColor(),
                    topbarTextColor = userPreferencesUseCase.getTopbarTextColor(),
                    sidebarTextColor = userPreferencesUseCase.getSidebarTextColor(),
                    screenTextColor = userPreferencesUseCase.getScreenTextColor(),
                    movieNoteTextColor = userPreferencesUseCase.getMovieNoteTextColor(),
                    dialogTextColor = userPreferencesUseCase.getDialogTextColor(),
                    originalTitleDisplay = userPreferencesUseCase.getOriginalTitleDisplay(),
                    dateFormat = userPreferencesUseCase.getDateFormat(),
                    notificationBeforeMonth = userPreferencesUseCase.getNotificationBeforeMonth(),
                    notificationBeforeWeek = userPreferencesUseCase.getNotificationBeforeWeek(),
                    notificationBeforeDay = userPreferencesUseCase.getNotificationBeforeDay(),
                    notificationOnDate = userPreferencesUseCase.getNotificationOnDate(),
                )
            isFavorite.value = isFavoriteMovie()
            loadMovieInfo()
        }
    }

    suspend fun loadMovieInfo() {
        movieInformation.value =
            movieDatabaseUseCase.getMovieItemResource(
                movieId = movieId,
                landscapeWidth = 500,
                posterWidth = 500,
                dateFormat = userPreferences.value.dateFormat
            )
    }

    fun handleMovieDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        movieDatabaseUseCase.handleImageDominantColor(drawable = drawable, onFinish = onFinish)
    }

    fun isFavoriteMovie(): Boolean = movieDatabaseUseCase.isFavoriteMovie(movieId)
    fun isWatchedMovie(): Boolean = movieDatabaseUseCase.isWatchedMovie(movieId)
    fun isSponsoredMovie(): Boolean = movieDatabaseUseCase.isSponsoredMovie(movieId)

    fun isSponsoredEdit(): Boolean {
        return user.value is Resource.Success && movieInformation.value.data != null && movieInformation.value.data!!.sponsored
    }

    suspend fun addFavoriteMovie(): Boolean {
        if (movieInformation.value.data != null) {
            return movieDatabaseUseCase.addFavoriteMovie(movieInformation.value.data!!)
        }
        return false
    }

    suspend fun deleteFavoriteMovie(): Boolean {
        return movieDatabaseUseCase.deleteFavoriteMovie(movieId)
    }

    fun updateIsFavorite() {
        isFavorite.value = !isFavorite.value
    }

    fun updateIsEditing() {
        isEditing.value = !isEditing.value
    }
}