package powerrangers.eivom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import powerrangers.eivom.feature_movie.ui.movie_detail.MovieDetailScreen
import powerrangers.eivom.feature_movie.ui.movie_list.MovieListScreen
import powerrangers.eivom.feature_movie.ui.movie_management.MovieManagementScreen
import powerrangers.eivom.feature_movie.ui.movie_note.MovieNoteScreen
import powerrangers.eivom.ui.app_information.AppInformationScreen
import powerrangers.eivom.ui.help_screen.HelpScreen
import powerrangers.eivom.ui.settings.SettingsScreen

@Composable
fun EivomNavigationHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.MOVIE_LIST_SCREEN
    ) {
        composable(
            route = Route.MOVIE_LIST_SCREEN
        ) {
            MovieListScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                },
                navigateToMovieDetailScreen = { movieId ->
                    navController.navigate(Route.MOVIE_DETAIL_SCREEN + "/${movieId}")
                }
            )
        }
        composable(
            route = Route.MOVIE_MANAGEMENT_SCREEN
        ) {
            MovieManagementScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                },
                navigateToSettingsScreen = {
                    navController.navigate(Route.SETTINGS)
                },
                navigateToMovieDetailScreen = { movieId ->
                    navController.navigate(Route.MOVIE_DETAIL_SCREEN + "/${movieId}")
                }
            )
        }
        composable(
            route = Route.MOVIE_DETAIL_SCREEN +
                    "/{${Route.MOVIE_DETAIL_SCREEN_MOVIE_ID}}",
            arguments = listOf(
                navArgument(Route.MOVIE_DETAIL_SCREEN_MOVIE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                },
                navigateToMovieNote = {
                    navController.navigate(Route.MOVIE_NOTE_SCREEN + "/${it.arguments?.getInt(Route.MOVIE_DETAIL_SCREEN_MOVIE_ID)}")
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = Route.MOVIE_NOTE_SCREEN +
                    "/{${Route.MOVIE_NOTE_SCREEN_MOVIE_ID}}",
            arguments = listOf(
                navArgument(Route.MOVIE_NOTE_SCREEN_MOVIE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieNoteScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = Route.APP_INFORMATION_SCREEN
        ) {
            AppInformationScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                }
            )
        }
        composable(
            route = Route.SETTINGS
        ) {
            SettingsScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                }
            )
        }
        composable(
            route = Route.HELP_SCREEN
        ) {
            HelpScreen(
                navigateToMenuItem = {
                    navController.navigate(it)
                }
            )
        }
    }
}