package powerrangers.eivom.feature_movie.domain.repository

import kotlinx.coroutines.flow.Flow
import powerrangers.eivom.feature_movie.data.utility.LocalMovieItem

interface LocalMovieDatabaseRepository {
    suspend fun insertLocalMovieItem(localMovieItem: LocalMovieItem)

    suspend fun updateLocalMovieItem(localMovieItem: LocalMovieItem)

    suspend fun deleteLocalMovieItemById(id: Int)

    fun getLocalMovieListItems(): Flow<List<LocalMovieItem>>

    fun getLocalMovieItem(movieId:Int): Flow<LocalMovieItem>

    fun getLocalMovieListItemsAsMap(): Flow<Map<Int, LocalMovieItem>>
}