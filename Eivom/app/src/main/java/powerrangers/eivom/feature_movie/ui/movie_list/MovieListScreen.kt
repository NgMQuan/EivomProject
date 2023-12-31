package powerrangers.eivom.feature_movie.ui.movie_list

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import powerrangers.eivom.R
import powerrangers.eivom.domain.utility.Resource
import powerrangers.eivom.domain.utility.ResourceErrorMessage
import powerrangers.eivom.feature_movie.domain.model.MovieListItem
import powerrangers.eivom.feature_movie.domain.utility.Order
import powerrangers.eivom.feature_movie.domain.utility.TranslateCode
import powerrangers.eivom.feature_movie.domain.utility.TrendingTime
import powerrangers.eivom.ui.component.BottomDefaultScreenBar
import powerrangers.eivom.ui.component.DrawerBody
import powerrangers.eivom.ui.component.DrawerHeader
import powerrangers.eivom.ui.component.FloatingAddButton
import powerrangers.eivom.ui.component.TopBar
import powerrangers.eivom.ui.theme.LobsterBold
import powerrangers.eivom.ui.theme.LobsterRegular
import powerrangers.eivom.ui.theme.Poppins
import powerrangers.eivom.ui.theme.PoppinsBold
import powerrangers.eivom.ui.theme.PoppinsItalic
import powerrangers.eivom.ui.theme.PoppinsMedium
import powerrangers.eivom.ui.theme.VintageKing
import powerrangers.eivom.ui.utility.UserPreferences
import java.lang.StrictMath.round
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    navigateToMenuItem: (String) -> Unit,
    navigateToMovieDetailScreen: (Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                onMenuIconClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        bottomBar = {
            BottomDefaultScreenBar()
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(onItemClick = navigateToMenuItem)
        },
        floatingActionButton = {
            FloatingAddButton(
                modifier = modifier.padding(20.dp),
                contentDescription = stringResource(id = R.string.add_favorite_movie_content_description),
                onClick = {
                    viewModel.updateAddingState(
                        isAdding = true
                    )
                }
            )
        }
    ) { innerPadding ->
        MovieListBody(
            modifier = modifier.padding(innerPadding),
            navigateToMovieDetailScreen = navigateToMovieDetailScreen
        )
    }
}

@Composable
fun MovieListBody(
    modifier: Modifier = Modifier,
    navigateToMovieDetailScreen: (Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val movieListItems by remember { viewModel.movieListItems }
    val isSearchVisible by remember { viewModel.isSearchVisible }
    val filterState by remember { viewModel.filterState  }
    val sortState by remember { viewModel.sortState }

    val isAddingMovie by remember { viewModel.isAddingMovie }

    if (isAddingMovie) {
        AddMovieDialog(
            modifier = Modifier,
            userPreferences = viewModel.userPreferences.value,
            updateAddingState = {
                viewModel.updateAddingState(it)
            }
        )
    }

    LaunchedEffect(key1 = filterState) {
        if (filterState.isUpdated) {
            viewModel.resetMovieList()
        }
    }

    LaunchedEffect(key1 = sortState){
        if (sortState.isUpdate){
            viewModel.resetMovieList()
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        AnimatedVisibility(
            visible = isSearchVisible,
            enter = slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeIn(),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeOut(),
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            // Content of the filter screen
            OutlinedTextField(
                value = viewModel.userSearch,
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .size(width = 310.dp, height = 60.dp)
                    .padding(horizontal = 10.dp)
                //.padding(start = 10.dp, end = 10.dp, top = 5.dp,)
                ,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                ),
                onValueChange = { viewModel.updateUserSearch(it) },
                label = {
                    Text(
                        text = stringResource(R.string.search_label),
                        fontFamily = PoppinsItalic,
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.primary
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_movie_placeholder),
                        style = TextStyle(
                            fontSize = 14.sp, // Adjust the font size as desired
                            color = Color.Gray
                        ),

                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon_outlineText),
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            viewModel.updateMovieListBySearch()
                        } //Click Search Button
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = PoppinsBold,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.updateMovieListBySearch()
                    }
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize(),
            ) {
                itemsIndexed(movieListItems.data!!) { index, movie ->
                    if (index >= movieListItems.data!!.size - 1 && movieListItems is Resource.Success) {
                        viewModel.loadMoviePaginated()
                    }
                    MovieListEntry(
                        modifier = modifier,
                        navigateToMovieDetailScreen = navigateToMovieDetailScreen,
                        movie = movie,
                        handleMovieDominantColor = { drawable, onFinish ->
                            viewModel.handleMovieDominantColor(
                                drawable = drawable,
                                onFinish = onFinish
                            )
                        },
                        addFavoriteMovie = {
                            val isSuccess = coroutineScope.async {
                                viewModel.addFavoriteMovie(it)
                            }
                            isSuccess.await()
                        },
                        deleteFavoriteMovie = {
                            val isSuccess = coroutineScope.async {
                                viewModel.deleteFavoriteMovie(it.id)
                            }
                            isSuccess.await()
                        },
                        isFavoriteMovie = { viewModel.isFavoriteMovie(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
            Box(
                contentAlignment = Center,
                modifier = Modifier.fillMaxSize()
            ) {
                when (movieListItems) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                    }
                    is Resource.Error -> {
                        RetrySection(
                            error = movieListItems.message ?: ResourceErrorMessage.UNKNOWN,
                            onRetry = {
                                viewModel.loadMoviePaginated()
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun AddMovieDialog(
    modifier: Modifier,
    userPreferences: UserPreferences,
    newLocalMovieViewModel: NewLocalMovieViewModel = hiltViewModel(),
    updateAddingState: (Boolean) -> Unit
) {
    val newMovieState by remember { newLocalMovieViewModel.newMovieState }

    val companies = remember { newLocalMovieViewModel.companies }

    Dialog(
        onDismissRequest = {
            updateAddingState(false)
        }
    ) {
        Card(
            elevation = 8.dp,
            modifier = modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Dialog title
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.add_manual_movie_title),
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = modifier
                        .height(450.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Adult
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(id = R.string.adult_label)
                        )
                        SelectButton(
                            modifier = modifier.weight(0.45f),
                            text = stringResource(id = R.string.adult_yes),
                            isSelected = newMovieState.adult == true,
                            onSelect = {
                                newLocalMovieViewModel.updateAdultOfMovie(
                                    isAdult = true
                                )
                            }
                        )
                        Spacer(modifier = modifier.weight(0.1f))
                        SelectButton(
                            modifier = modifier.weight(0.45f),
                            text = stringResource(id = R.string.adult_no),
                            isSelected = newMovieState.adult == false,
                            onSelect = {
                                newLocalMovieViewModel.updateAdultOfMovie(
                                    isAdult = false
                                )
                            }
                        )
                    }
                    // Original Title
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.originalTitle ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieOriginalTitle(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.original_title_field_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.original_title_field_placeholder))
                        },
                        maxLines = 2
                    )
                    // Title
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.title ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieTitle(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.title_field_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.title_field_placeholder))
                        },
                        maxLines = 2
                    )
                    // Genres
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(id = R.string.genre_label)
                        )
                        LazyRow(
                            modifier = modifier.weight(2f)
                        ) {
                            items(newLocalMovieViewModel.genreList) { genre ->
                                val isGenreSelected = newLocalMovieViewModel.isGenreSelected(genre.second)
                                SelectButton(
                                    text = genre.second,
                                    isSelected = isGenreSelected,
                                    onSelect = {
                                        if (isGenreSelected) {
                                            newLocalMovieViewModel.removeMovieGenre(genre.second)
                                        } else {
                                            newLocalMovieViewModel.addMovieGenre(genre.second)
                                        }
                                    }
                                )
                            }
                        }
                    }
                    // Overview
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.overview ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieOverview(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.overview_field_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.overview_field_placeholder))
                        },
                        maxLines = 8
                    )
                    // Original Language
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val density = LocalDensity.current

                        var isOriginalLanguageDropdownExpanded by remember { mutableStateOf(false) }
                        var dropdownMenuWidth by remember { mutableStateOf(0.dp) }

                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(id = R.string.original_language_label)
                        )
                        Column(
                            modifier = modifier.weight(1f)
                        ) {
                            SelectButton(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        dropdownMenuWidth =
                                            (coordinates.size.width / density.density).dp
                                    },
                                text = TranslateCode.ISO_639_1[newMovieState.originalLanguage]
                                    ?: stringResource(id = R.string.unknown),
                                isSelected = newMovieState.originalLanguage != null,
                                onSelect = {
                                    isOriginalLanguageDropdownExpanded =
                                        !isOriginalLanguageDropdownExpanded
                                }
                            )
                            DropdownMenu(
                                modifier = modifier
                                    .height(200.dp)
                                    .width(dropdownMenuWidth),
                                expanded = isOriginalLanguageDropdownExpanded,
                                onDismissRequest = {
                                    isOriginalLanguageDropdownExpanded = false
                                }
                            ) {
                                newLocalMovieViewModel.languageList.forEach { language ->
                                    DropdownMenuItem(
                                        onClick = {
                                            newLocalMovieViewModel.updateMovieOriginalLanguage(
                                                language.first
                                            )
                                            newLocalMovieViewModel.removeMovieSpokenLanguage(
                                                language.first
                                            )
                                            isOriginalLanguageDropdownExpanded = false
                                        }
                                    ) {
                                        Text(
                                            modifier = modifier.fillMaxWidth(),
                                            text = language.second,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    // Spoken Language
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val density = LocalDensity.current
                        var dropdownMenuWidth by remember { mutableStateOf(0.dp) }
                        var isLanguageDropdownExpanded by remember { mutableStateOf(false) }

                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(id = R.string.spoken_language_label)
                        )
                        AddOrDeleteButton(
                            modifier = modifier
                                .weight(1f)
                                .onGloballyPositioned { coordinates ->
                                    dropdownMenuWidth =
                                        (coordinates.size.width / density.density).dp
                                },
                            isAddButton = true,
                            contentDescription = stringResource(id = R.string.add_spoken_language_content_description),
                            onClick = {
                                isLanguageDropdownExpanded =
                                    !isLanguageDropdownExpanded
                            }
                        )
                        DropdownMenu(
                            modifier = modifier
                                .height(200.dp)
                                .width(dropdownMenuWidth),
                            expanded = isLanguageDropdownExpanded,
                            onDismissRequest = {
                                isLanguageDropdownExpanded = false
                            }
                        ) {
                            newLocalMovieViewModel.languageList.forEach { language ->
                                if (language.first != newMovieState.originalLanguage && language.first !in newMovieState.spokenLanguages) {
                                    DropdownMenuItem(
                                        onClick = {
                                            newLocalMovieViewModel.addMovieSpokenLanguage(
                                                language.first
                                            )
                                            isLanguageDropdownExpanded = false
                                        }
                                    ) {
                                        Text(
                                            modifier = modifier.fillMaxWidth(),
                                            text = language.second,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Spacer(modifier = modifier.weight(1f))

                        Column(
                            modifier = modifier
                                .weight(1f)
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                        ) {
                            if (newMovieState.originalLanguage != null) {
                                SelectButton(
                                    modifier = modifier.fillMaxWidth(),
                                    enabled = false,
                                    text = TranslateCode.ISO_639_1[newMovieState.originalLanguage]
                                        ?: stringResource(id = R.string.unknown),
                                    isSelected = true,
                                    onSelect = {}
                                )
                            }
                            for (i in newMovieState.spokenLanguages.lastIndex downTo 0) {
                                SelectButton(
                                    modifier = modifier.fillMaxWidth(),
                                    text = TranslateCode.ISO_639_1[newMovieState.spokenLanguages[i]]
                                        ?: stringResource(
                                            id = R.string.unknown
                                        ),
                                    isSelected = false,
                                    onSelect = {
                                        newLocalMovieViewModel.removeMovieSpokenLanguage(
                                            newMovieState.spokenLanguages[i]
                                        )
                                    }
                                )
                            }
                        }
                    }
                    // Length
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.length?.toString() ?: "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieLength(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.length_field_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.length_field_placeholder))
                        },
                        trailingIcon = {
                            Text(text = stringResource(id = R.string.length_unit))
                        },
                        maxLines = 1
                    )
                    // Release Date
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val context = LocalContext.current
                        val currentDate = LocalDate.now()

                        Text(
                            modifier = modifier.weight(1f),
                            text = stringResource(id = R.string.release_date_label)
                        )
                        SelectButton(
                            modifier = modifier.weight(1f),
                            text = newMovieState.regionReleaseDate?.format(userPreferences.dateFormat)
                                ?: stringResource(id = R.string.unknown),
                            isSelected = newMovieState.regionReleaseDate != null,
                            onSelect = {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        newLocalMovieViewModel.updateMovieReleaseDate(
                                            year = year,
                                            month = month + 1,
                                            dayOfMonth = dayOfMonth
                                        )
                                    },
                                    newMovieState.regionReleaseDate?.year ?: currentDate.year,
                                    (newMovieState.regionReleaseDate?.monthValue
                                        ?: currentDate.monthValue) - 1,
                                    newMovieState.regionReleaseDate?.dayOfMonth ?: currentDate.dayOfMonth
                                ).show()
                            }
                        )
                    }
                    // Company
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = modifier.weight(1f),
                                text = stringResource(id = R.string.company_label)
                            )
                            AddOrDeleteButton(
                                modifier = modifier.weight(1f),
                                isAddButton = true,
                                contentDescription = stringResource(id = R.string.add_company_content_description),
                                onClick = {
                                    newLocalMovieViewModel.addMovieCompany()
                                }
                            )
                        }
                        for (i in companies.lastIndex downTo 0) {
                            OutlinedTextField(
                                modifier = modifier.fillMaxWidth(),
                                value = companies[i],
                                onValueChange = {
                                    newLocalMovieViewModel.updateMovieCompany(
                                        index = i,
                                        name = it
                                    )
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.company_name_label))
                                },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.company_name_placeholder))
                                },
                                maxLines = 1,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            newLocalMovieViewModel.removeMovieCompany(i)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = stringResource(id = R.string.remove_company_button)
                                        )
                                    }
                                }
                            )
                        }
                    }
                    // Main Poster Url
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.posterUrl ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMoviePosterUrl(
                                url = it
                            )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.movie_main_poster_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.movie_main_poster_placeholder))
                        },
                        maxLines = 1
                    )
                    // Main Backdrop Url
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.landscapeImageUrl ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieLandscapeImageUrl(
                                url = it
                            )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.movie_main_backdrop_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.movie_main_backdrop_placeholder))
                        },
                        maxLines = 1
                    )
                    // Homepage Url
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.homepageUrl ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieHomepage(
                                url = it
                            )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.movie_homepage_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.movie_homepage_placeholder))
                        },
                        maxLines = 1
                    )
                    // Status
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.status ?: "",
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieStatus(
                                status = it
                            )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.status_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.status_placeholder))
                        },
                        maxLines = 1
                    )
                    // Budget
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.budget?.toString() ?: "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieBudget(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.budget_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.budget_placeholder))
                        },
                        trailingIcon = {
                            Text(text = stringResource(id = R.string.currency_unit))
                        },
                        maxLines = 1
                    )
                    // Revenue
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = newMovieState.revenue?.toString() ?: "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            newLocalMovieViewModel.updateMovieRevenue(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.revenue_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.revenue_placeholder))
                        },
                        trailingIcon = {
                            Text(text = stringResource(id = R.string.currency_unit))
                        },
                        maxLines = 1
                    )
                }
                // Button
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    Button(
                        onClick = {
                            updateAddingState(false)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.cancel_button))
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (newLocalMovieViewModel.saveNewMovie()) {
                                    newLocalMovieViewModel.clearNewMovieState()
                                    updateAddingState(false)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.add_sponsored_movie_success_notification),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.add_sponsored_movie_error_notification),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },
                        enabled = newLocalMovieViewModel.isNewMovieValid()
                    ) {
                        Text(text = stringResource(id = R.string.save_button_title))
                    }
                }
            }
        }
    }
}

@Composable
fun SelectButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            contentColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
        )
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AddOrDeleteButton(
    modifier: Modifier = Modifier,
    isAddButton: Boolean,
    contentDescription: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isAddButton) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            contentColor = if (isAddButton) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
        )
    ) {
        Icon(
            imageVector = if (isAddButton) Icons.Filled.Add else Icons.Filled.Delete,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun MovieListEntry(
    modifier: Modifier = Modifier,
    movie: MovieListItem,
    navigateToMovieDetailScreen: (Int) -> Unit,
    handleMovieDominantColor: (Drawable, (Color) -> Unit) -> Unit,
    addFavoriteMovie: suspend (MovieListItem) -> Boolean,
    deleteFavoriteMovie: suspend (MovieListItem) -> Boolean,
    isFavoriteMovie: (Int) -> Boolean
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(Color.Black)
    }
    var isFavorite by remember {
        mutableStateOf(isFavoriteMovie(movie.id))
    }
    val coroutineScope = rememberCoroutineScope()
    val addError = stringResource(id = R.string.add_favorite_movie_failure)
    val deleteError = stringResource(id = R.string.delete_favorite_movie_failure)
    val showErrorDialog = remember { mutableStateOf(false) }
    val errorDescription = remember { mutableStateOf("") }

    if (showErrorDialog.value) {
        ErrorDialog(
            error = errorDescription.value,
            onRetry = { showErrorDialog.value = false },
            onDismiss = { showErrorDialog.value = false }
        )
    }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .height(430.dp)
            .width(300.dp)
            .clickable {
                navigateToMovieDetailScreen(movie.id)
            }
    ) {
        val posterUrl = movie.posterUrl
        val onErrorFallbackImageRes = painterResource(id = R.drawable.error_img)
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterUrl)
                .crossfade(true)
                .build()
        )
        Row(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)){
                Image(
                    painter = if (painter.state is AsyncImagePainter.State.Error) onErrorFallbackImageRes else painter,
                    contentDescription = null,
                    modifier = modifier
                        .padding(top = 30.dp, start = 5.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .fillMaxHeight()
                        .width(260.dp)
                    ,
                    alignment = Center,
                    contentScale = ContentScale.Crop,
                )
                FavoriteMovieButton(
                    isFavorite = isFavorite,
                    onFavoriteToggle = { isChecked ->
                        coroutineScope.launch {
                            val isSuccess = if (isFavorite) {
                                errorDescription.value = deleteError
                                deleteFavoriteMovie(movie)
                            } else {
                                errorDescription.value = addError
                                addFavoriteMovie(movie)
                            }
                            if (isSuccess) {
                                isFavorite = isChecked
                            } else {
                                showErrorDialog.value = true
                            }
                        }
                    },
                    checkedColor = Color.Red,
                    uncheckedColor = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .align(TopEnd)
                        .padding(top = 30.dp)
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1.2f)
                .padding(top = 20.dp)) {
                Text(
                    text = movie.title,
                    textAlign = if (movie.title.length < 18) TextAlign.Left else TextAlign.Center,
                    fontSize = if (movie.title.length < 16) 24.sp else 20.sp,
                    fontFamily = PoppinsBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .padding(top = 5.dp),
                        tint = MaterialTheme.colors.secondary
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp)
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                text = "${round(movie.voteAverage.toFloat() * 1000).toFloat() / 1000}",
                                fontSize = 18.sp,
                                fontFamily = PoppinsBold,
                                color = MaterialTheme.colors.secondary,
                                modifier = Modifier.padding(bottom = 1.dp)
                            )
                            Text(
                                text = " /10",
                                fontSize = 14.sp,
                                fontFamily = Poppins,
                                color = Color.LightGray,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }
                        var vote = movie.voteCount.toInt()
                        if (vote >= 1000 && vote <= 1000000){
                            vote = vote / 1000
                            Text(
                                text = "${vote}K",
                                fontSize = 16.sp,
                                fontFamily = PoppinsBold,
                                color = Color.Gray
                            )
                        }
                        else if ( vote > 1000000){
                            vote = vote / 1000000
                            Text(
                                text = "${vote}M",
                                fontSize = 16.sp,
                                fontFamily = PoppinsBold,
                                color = Color.Gray
                            )
                        }
                        else Text(
                            text = "${vote}",
                            fontSize = 16.sp,
                            fontFamily = PoppinsBold,
                            color = Color.Gray
                        )
                    }
                }
                // Genres
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ){
                    val item = 0
                    if (!movie.genres.isEmpty()){
                        if (movie.genres.size < 2) {
                            Text(
                                text = "${movie.genres[item]}, ",
                                fontSize = 14.sp,
                                fontFamily = PoppinsMedium,
                                color = Color.Black
                            )
                        }
                        else
                        if (movie.genres.size < 3) {
                            Text(
                                text = "${movie.genres[item]}, ${movie.genres[item+1]}",
                                fontSize = 14.sp,
                                fontFamily = PoppinsMedium,
                                color = Color.Black
                            )
                        }
                        else
                        if (movie.genres.size < 4) {
                            Text(
                                text = "${movie.genres[item]}, ${movie.genres[item+1]}, ${movie.genres[item+2]}",
                                fontSize = 14.sp,
                                fontFamily = PoppinsMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
                if (!movie.overview.isNullOrBlank())
                {
                    val length = if (movie.overview.length < 80) movie.overview.length else 80
                    var subString = movie.overview
                    subString = subString.substring(0, length)
                    Text(
                        text = "${subString} . . . ",
                        fontSize = 16.sp,
                        fontFamily = Poppins,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }

            }

        }
    }
}

@Composable
fun FavoriteMovieButton(
    isFavorite: Boolean,
    onFavoriteToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedColor: Color = MaterialTheme.colors.primary,
    uncheckedColor: Color = MaterialTheme.colors.onSurface
) {
    val favoriteIcon = if (isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Default.Favorite
    }

    IconButton(
        onClick = { onFavoriteToggle(!isFavorite) },
        modifier = modifier
    ) {
        Icon(
            imageVector = favoriteIcon,
            contentDescription = if (isFavorite) {
                stringResource(R.string.favorite_movie_description)
            } else {
                stringResource(R.string.unfavorite_movie_description)
            },
            tint = if (isFavorite) checkedColor else uncheckedColor
        )
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}

@Composable
fun TrendingFilter(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val trending by remember {viewModel.trendingFilter}
    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (trending == null) Color.White else MaterialTheme.colors.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.primary)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_trend),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = 3.dp,
                        bottom = 5.dp,
                        start = 10.dp,
                        end = 9.dp
                    )
                ,
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.Trending_FilterState),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.White,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {viewModel.reverseIsTrending()} ) {
                if (trending == null)
                    Icon(
                        painter = painterResource(R.drawable.unchecked_ic),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .padding(bottom = 5.dp),
                        tint = Color.White
                    )
                else Icon(
                    painter = painterResource(R.drawable.checked_ic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(bottom = 5.dp),
                    tint = Color.White
                )
            }
        }

        if (trending != null)
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = MaterialTheme.colors.secondary)
            ) {
                Text(
                    text = stringResource(id = R.string.TrendingDay_FilterState),
                    fontSize = 16.sp,
                    fontFamily = PoppinsBold,
                    color = Color.White,
                    modifier = Modifier.padding(
                        vertical = 5.dp,
                        horizontal = 10.dp
                    )
                )
                Spacer( modifier = Modifier.weight(0.10f))

                IconButton(onClick = { viewModel.reverseTrendingDayWeek() } ) {
                    if (trending != TrendingTime.DAY)
                        Icon(
                            painter = painterResource(R.drawable.unchecked_ic),
                            contentDescription = null,
                            modifier = Modifier.size(21.dp),
                            tint = Color.White,
                        )
                    else    Icon(
                        painter = painterResource(R.drawable.checked_ic),
                        contentDescription = null,
                        modifier = Modifier.size(21.dp),
                        tint = Color.White,
                    )

                }

                Spacer( modifier = Modifier.weight(0.25f))

                Text(
                    text = stringResource(id = R.string.TrendingWeek_FilterState),
                    fontSize = 16.sp,
                    fontFamily = PoppinsBold,
                    color = Color.White,
                    modifier = Modifier.padding(
                        vertical = 5.dp,
                        horizontal = 10.dp
                    )
                )
                Spacer( modifier = Modifier.weight(0.10f))

                IconButton(onClick = { viewModel.reverseTrendingDayWeek() } ) {
                    if (trending != TrendingTime.WEEK)
                        Icon(
                            painter = painterResource(R.drawable.unchecked_ic),
                            contentDescription = null,
                            modifier = Modifier.size(21.dp),
                            tint = Color.White,
                        )
                    else Icon(
                        painter = painterResource(R.drawable.checked_ic),
                        contentDescription = null,
                        modifier = Modifier.size(21.dp),
                        tint = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteFilter(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val favorite by remember { viewModel.favoriteFilter }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Favorite_FilterState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { if (viewModel.trendingFilter.value == null ) viewModel.reverseIsFavorite()} ) {
            if (favorite == null)
                Icon(
                    painter = painterResource(R.drawable.unchecked_ic),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = Color.White,
                )
            else
                Icon(
                painter = painterResource(R.drawable.checked_ic),
                contentDescription = null,
                modifier = Modifier.size(21.dp),
                tint = Color.White,
            )
        }
    }
}

@Composable
fun WatchedFilter(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val watched by remember {viewModel.watchedFilter}
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_watched),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Watched_FilterState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { if (viewModel.trendingFilter.value == null) viewModel.reverseIsWatched()} ) {
            if (watched == null)
                Icon(
                    painter = painterResource(R.drawable.unchecked_ic),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = Color.White,
                )
            else
                Icon(
                    painter = painterResource(R.drawable.checked_ic),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = Color.White,
                )
        }
    }
}

@Composable
fun RegionFilter(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val regions = TranslateCode.ISO_3166_1.values.toList()
    val regionSelected by remember { viewModel.regionSelected }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_region),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Region_FilterState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        // Region selection
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
                .width(130.dp)
                .height(35.dp)
                .padding(
                    end = 7.dp
                )
                .clip(RoundedCornerShape(20.dp))
        ) {
            OutlinedButton(
                onClick = {
                    if (regionSelected.isEmpty())
                    {
                        viewModel.changeRegionSelect(regions[0])
                    }
                    else {
                        viewModel.resetRegionSelect()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = regionSelected.ifEmpty { "Select Region" },
                    textAlign = TextAlign.Center,
                    fontFamily = PoppinsBold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                DropdownMenu(
                    expanded = regionSelected.isEmpty(),
                    onDismissRequest = {
                        viewModel.resetRegionSelect()
                    },
                    modifier = Modifier.border(2.dp, Color.LightGray)
                ) {
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .heightIn(max = dropdownMenuHeight(visibleItems = 4))
                            .verticalScroll(rememberScrollState())
                    ){
                        regions.forEach { region ->
                            DropdownMenuItem(onClick = { viewModel.changeRegionSelect(region) }) {
                                Text(
                                    text = region,
                                    fontFamily = PoppinsBold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colors.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun dropdownMenuHeight(visibleItems: Int): Dp {
    val itemHeight = 48.dp // Height of each item in the dropdown menu
    val padding = 8.dp // Vertical padding between items

    return (visibleItems * itemHeight) + ((visibleItems - 1) * padding)
}

@Composable
fun AdultConTentFilter(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val isAdult by remember {viewModel.adultFilter}
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_adultcontetn),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.AdultContentIncluded_FilterState),
            fontSize = 18.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { if (viewModel.trendingFilter.value == null) viewModel.reverseAdultContent()} ) {
            if (isAdult == null)
                Icon(
                    painter = painterResource(R.drawable.unchecked_ic),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = Color.White,
                )
            else
                Icon(
                    painter = painterResource(R.drawable.checked_ic),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = Color.White,
                )
        }
    }
}

@Composable
fun ReleaseDateFilter(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newRD by remember { viewModel.releaseDate }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_year),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.ReleaseYear_FilterState),
            fontSize = 19.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            OutlinedTextField(
                value = newRD,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    if (newValue.length <= 4) {
                        viewModel.updateReleaseDate(filteredValue)
                    }
                    else {
                        viewModel.updateReleaseDate("")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left,
                    //background = Color.White
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor =  Color.Gray,
                    placeholderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                ),
                placeholder = {
                    Text(
                        text = LocalDate.now().year.toString(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = PoppinsBold,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun MinDateFilter(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newMinRD by remember { viewModel.minReleaseDate }
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = stringResource(id = R.string.MinimumReleaseDate_FilterState),
            fontSize = 18.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 22.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = {
                val calendar = Calendar.getInstance()
                newMinRD?.let {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    calendar.time = newMinRD?.let { it1 -> dateFormat.parse(it1) } ?: calendar.time
                }

                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val newcalendar = Calendar.getInstance()
                        newcalendar.set(year, month, dayOfMonth)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        viewModel.updateMinReleaseDate(dateFormat.format(newcalendar.time))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
        ) {
            Icon(
                Icons.Default.DateRange, contentDescription = "Select a date",
                tint = Color.White,
                modifier = Modifier
                    .size(38.dp)
                    .padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun MaxDateFilter(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newMaxRD by remember { viewModel.maxReleaseDate }
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = stringResource(id = R.string.MaximumReleaseDate_FilterState),
            fontSize = 18.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 22.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                val calendar = Calendar.getInstance()
                newMaxRD?.let {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    calendar.time = newMaxRD?.let { it1 -> dateFormat.parse(it1) } ?: calendar.time
                }

                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val newcalendar = Calendar.getInstance()
                        newcalendar.set(year, month, dayOfMonth)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        viewModel.updateMaxReleaseDate(dateFormat.format(newcalendar.time))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
        ) {
            Icon(
                Icons.Default.DateRange, contentDescription = "Select a date",
                tint = Color.White,
                modifier = Modifier
                    .size(38.dp)
                    .padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun MinRating(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newFloat by remember { viewModel.minRating }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_starcard),
            contentDescription = null,
            modifier = Modifier
                .size(41.dp)
                .padding(
                    top = 3.dp,
                    bottom = 9.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.MinimumRating_FilterState),
            fontSize = 22.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        ){
            OutlinedTextField(
                value = newFloat?: "",
                onValueChange = { newValue ->
                    // Validate the input to ensure it falls within the desired range
                    if ( newValue.length < 3 ||
                        (
                                newValue.length == 3 && newValue.toFloat() in 0.0f..10.0f
                                )
                    ){
                        viewModel.updateMinRating(newValue)
                    } else {
                        // If the input is not a valid float within the range, do not update the value
                        viewModel.updateMinRating("")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done // Set the IME action to "Done"
                ),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor =  Color.Gray,
                    placeholderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                ),
                placeholder = {
                    Text(
                        text = "1.0",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = PoppinsBold,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }
}

@Composable
fun MaxRating(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newFloat by remember { viewModel.maxRating }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_starcard),
            contentDescription = null,
            modifier = Modifier
                .size(41.dp)
                .padding(
                    top = 3.dp,
                    bottom = 9.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.MaximumRating_FilterState),
            fontSize = 22.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        ){
            OutlinedTextField(
                value = newFloat?: "",
                onValueChange = { newValue ->
                    // Validate the input to ensure it falls within the desired range
                    if ( newValue.length < 3 ||
                        (
                                newValue.length == 3 && newValue.toFloat() in 0.0f..10.0f
                                )
                    ){
                        viewModel.updateMaxRating(newValue)
                    } else {
                        // If the input is not a valid float within the range, do not update the value
                        viewModel.updateMaxRating("")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done // Set the IME action to "Done"
                ),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor =  Color.Gray,
                    placeholderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,

                    ),
                placeholder = {
                    Text(
                        text = "5.0",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = PoppinsBold,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun MinLength(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newLen by remember { viewModel.minLength }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_movielen),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 8.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.MinimumLength_FilterState),
            fontSize = 20.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        ){
            OutlinedTextField(
                value = newLen?: "",
                onValueChange = { newValue ->
                    // Validate the input to ensure it falls within the desired range
                    if ( newValue.length < 3 ||
                        (
                                newValue.length == 3 && newValue.toInt() in 60..200
                                )
                    ){
                        viewModel.updateMinLen(newValue)
                    } else {
                        // If the input is not a valid float within the range, do not update the value
                        viewModel.updateMinLen("")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done // Set the IME action to "Done"
                ),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor =  Color.Gray,
                    placeholderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                    trailingIconColor = MaterialTheme.colors.primary,
                    disabledTrailingIconColor = Color.Gray
                ),
                placeholder = {
                    Text(
                        text = "90",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = PoppinsBold,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@Composable
fun MaxLength(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val newLen by remember { viewModel.maxLength }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_movielen),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 8.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.MaximumLength_FilterState),
            fontSize = 20.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        ){
            OutlinedTextField(
                value = newLen?: "",
                onValueChange = { newValue ->
                    // Validate the input to ensure it falls within the desired range
                    if ( newValue.length < 3 ||
                        (
                                newValue.length == 3 && newValue.toInt() in 60..200
                                )
                    ){
                        viewModel.updateMaxLen(newValue)
                    } else {
                        // If the input is not a valid float within the range, do not update the value
                        viewModel.updateMaxLen("")
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done // Set the IME action to "Done"
                ),
                visualTransformation = VisualTransformation.None,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor =  Color.Gray,
                    placeholderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.primary,
                    backgroundColor = Color.White,
                    trailingIconColor = MaterialTheme.colors.primary,
                    disabledTrailingIconColor = Color.Gray

                ),
                placeholder = {
                    Text(
                        text = "150",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = PoppinsBold,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}


@Composable
fun GenreFilter(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val genres = TranslateCode.GENRE.values.toList()
    val allGenres: List<GenreItems> = genres.map { GenreItems(name = it) }
    val selectedGenres = remember{ viewModel.selectedGenres }
    val isGenre by remember { viewModel.isGenres }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (!isGenre) Color.White else MaterialTheme.colors.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.primary)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_genre),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = 3.dp,
                        bottom = 5.dp,
                        start = 10.dp,
                        end = 9.dp
                    )
                ,
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.Genre_FilterState),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.White,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Edit more
            IconButton(onClick = {viewModel.reverseIsGenres()} ) {
                if (!isGenre)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                else Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        if (isGenre) {
            GenreSelectMenu(genres = allGenres, selectedGenres = selectedGenres)
        }
    }

}


@Composable
fun GenreSelectMenu(
    genres: List<GenreItems>,
    selectedGenres: MutableList<GenreItems>
) {
    val rows = genres.chunked(3) // Group genres into rows with three items each
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.secondary)
    ) {
        for (rowGenres in rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (genre in rowGenres) {
                    GenreCard(genre, selectedGenres)
                }
            }
        }
    }
}

@Composable
fun GenreCard(
    genre: GenreItems,
    selectedGenres: MutableList<GenreItems>
) {
    val isExist = selectedGenres.any{ Selectitem -> Selectitem.name == genre.name }
    if (isExist) genre.isSelected.value = true
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                genre.isSelected.value = !genre.isSelected.value
                if (genre.isSelected.value) {
                    if (!isExist) selectedGenres.add(genre)
                } else {
                    if (isExist) selectedGenres.remove(genre)
                }
            }
            .background(
                color = if (genre.isSelected.value) MaterialTheme.colors.primary else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(align = Center)
                .widthIn(min = 60.dp, max = 125.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if (genre.isSelected.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        //.padding(start = 4.dp)
                        .size(10.dp),
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        //.padding(start = 4.dp)
                        .size(10.dp),
                    tint = MaterialTheme.colors.primary
                )
            }

            Text(
                text = genre.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (genre.isSelected.value) Color.White else MaterialTheme.colors.primary,
                textAlign = TextAlign.Center // Center the text horizontally within the card
            )
        }

    }
}


@Composable
fun WithoutGenreFilter(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val genres = TranslateCode.GENRE.values.toList()
    val allGenres: List<GenreItems> = genres.map { GenreItems(name = it) }
    val selectedWithoutGenres = remember { viewModel.selectedWithoutGenres }
    val isWithoutGenre by remember { viewModel.isWithoutGenres }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (!isWithoutGenre) Color.White else MaterialTheme.colors.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.primary)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_genre),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = 3.dp,
                        bottom = 5.dp,
                        start = 10.dp,
                        end = 9.dp
                    )
                ,
                tint = Color.White
            )

            Text(
                text = stringResource(id = R.string.WithoutGenre_FilterState),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.White,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Edit more
            IconButton(onClick = {viewModel.reverseIsWithoutGenres()} ) {
                if (!isWithoutGenre)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                else Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        if (isWithoutGenre) {
            WithoutGenreSelectMenu(genres = allGenres, selectedWithoutGenres = selectedWithoutGenres, selectedGenres = viewModel.selectedGenres)
        }
    }

}


@Composable
fun WithoutGenreSelectMenu(
    genres: List<GenreItems>,
    selectedWithoutGenres: MutableList<GenreItems>,
    selectedGenres: MutableList<GenreItems>
) {
    val rows = genres.chunked(3) // Group genres into rows with three items each
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.secondary)
    ) {
        for (rowGenres in rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (genre in rowGenres) {
                    WithoutGenreCard(genre, selectedWithoutGenres, selectedGenres)
                }
            }
        }
    }
}

@Composable
fun WithoutGenreCard(
    genre: GenreItems,
    selectedWithoutGenres: MutableList<GenreItems>,
    selectedGenres: MutableList<GenreItems>
) {
    val checkExist = selectedGenres.any{ genreItems -> genreItems.name == genre.name }
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                if (!checkExist) {
                    genre.isSelected.value = !genre.isSelected.value
                    if (genre.isSelected.value) {
                        selectedWithoutGenres.add(genre)
                    } else {
                        selectedWithoutGenres.remove(genre)
                    }
                }
            }
            .background(
                color =
                if (genre.isSelected.value && !checkExist) MaterialTheme.colors.primary
                else if (checkExist) Color.LightGray
                else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(align = Center)
                .widthIn(min = 60.dp, max = 120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            Text(
                text = genre.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (genre.isSelected.value) {
                            Color.White
                        }
                        else if (checkExist){
                            Color.Gray
                        }
                        else MaterialTheme.colors.primary,
                textAlign = TextAlign.Center // Center the text horizontally within the card
            )
        }

    }
}


@Composable
fun CountryFilter(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val countries = TranslateCode.ISO_3166_1.values.toList()
    val allCountries: List<Countries> = countries.map { Countries(name = it) }
    val selectedCountries = remember{ viewModel.selectedCountries }
    val isCountry by remember { viewModel.isCountry }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (!isCountry) Color.White else MaterialTheme.colors.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.primary)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_country),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = 3.dp,
                        bottom = 7.dp,
                        start = 10.dp,
                        end = 9.dp
                    )
                ,
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.OriginCountry_FilterState),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.White,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Edit more
            IconButton(onClick = {viewModel.reverseIsCountry()} ) {
                if (!isCountry)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                else Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        if (isCountry) {
            CountrySelectMenu(countries = allCountries, selectedCountries = selectedCountries)
        }
    }

}


@Composable
fun CountrySelectMenu(
    countries: List<Countries>,
    selectedCountries: MutableList<Countries>
) {
    val rows = countries.chunked(3) // Group genres into rows with three items each
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.secondary)
    ) {
        items(rows) { rowCountries ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (country in rowCountries) {
                    CountryCard(country, selectedCountries)
                }
            }
        }
    }
}


@Composable
fun CountryCard(
    country: Countries,
    selectedCountries: MutableList<Countries>
) {
    val isExist = selectedCountries.any{ Selectitem -> Selectitem.name == country.name }
    if (isExist) country.isSelected.value = true
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                country.isSelected.value = !country.isSelected.value
                if (country.isSelected.value) {
                    if (!isExist) selectedCountries.add(country)
                } else {
                    if (isExist) selectedCountries.remove(country)
                }
            }
            .background(
                color = if (country.isSelected.value) MaterialTheme.colors.primary else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(align = Center)
                .widthIn(min = 60.dp, max = 120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if (country.isSelected.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .size(10.dp),
                    tint = Color.White
                )
            }
            Text(
                text = country.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (country.isSelected.value) Color.White else MaterialTheme.colors.primary,
                textAlign = TextAlign.Center // Center the text horizontally within the card
            )
        }

    }
}

@Composable
fun LanguageFilter(
    viewModel: MovieListViewModel = hiltViewModel()
){
    val language = TranslateCode.ISO_639_1.values.toList()
    val allLanguages: List<Language> = language.map { Language(name = it) }
    val selectedLanguages = remember{ viewModel.selectedLanguage }
    val isLanguage by remember { viewModel.isLanguage }

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = if (!isLanguage) Color.White else MaterialTheme.colors.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.primary)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_language),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(
                        top = 3.dp,
                        bottom = 5.dp,
                        start = 10.dp,
                        end = 9.dp
                    )
                ,
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.OriginLanguage_FilterState),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = Color.White,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Edit more
            IconButton(onClick = {viewModel.reverseIsLanguage()} ) {
                if (!isLanguage)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                else Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        if (isLanguage) {
            LanguageSelectMenu(languages = allLanguages, selectedLanguages = selectedLanguages )
        }
    }

}


@Composable
fun LanguageSelectMenu(
    languages: List<Language>,
    selectedLanguages: MutableList<Language>
) {
    val rows = languages.chunked(3) // Group genres into rows with three items each
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.secondary)
    ) {
        items(rows) { rowLanguages ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (language in rowLanguages) {
                    LanguageCard(language = language, selectedLanguages = selectedLanguages)
                }
            }
        }
    }
}


@Composable
fun LanguageCard(
    language: Language,
    selectedLanguages: MutableList<Language>
) {
    val isExist = selectedLanguages.any{ Selectitem -> Selectitem.name == language.name }
    if (isExist) language.isSelected.value = true
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                language.isSelected.value = !language.isSelected.value
                if (language.isSelected.value) {
                    if (!isExist) selectedLanguages.add(language)
                } else {
                    if (isExist) selectedLanguages.remove(language)
                }
            }
            .background(
                color = if (language.isSelected.value) MaterialTheme.colors.primary else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row (
            modifier = Modifier
                .padding(10.dp)
                .wrapContentSize(align = Center)
                .widthIn(min = 60.dp, max = 120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if (language.isSelected.value) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .size(10.dp),
                    tint = Color.White
                )
            }
            Text(
                text = language.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (language.isSelected.value) Color.White else MaterialTheme.colors.primary,
                textAlign = TextAlign.Center // Center the text horizontally within the card
            )
        }

    }
}

@Composable
fun FilterDialog(
    funcToCall: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    viewModel.updateFilterViewModel()

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true),
        content = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .width(340.dp)
                    .height(450.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.filter_title),
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontFamily = LobsterRegular,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .weight(0.2f)
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .weight(1.2f)
                ) {
                    // Trending Filter
                    item{
                        TrendingFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(5.dp)) }
                    // Favorite Filter
                    item{
                        FavoriteFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(5.dp)) }
                       // Watched Filter
                       item{
                            WatchedFilter( viewModel = viewModel)
                        }
                    item { Spacer(modifier = Modifier.height(5.dp)) }
                        // Adult Content Filter
                       item{
                            AdultConTentFilter( viewModel = viewModel)
                        }
                    item { Spacer(modifier = Modifier.height(5.dp)) }
                    // Region Filter
                    item{
                        RegionFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Release Date Filter
                    item{
                        ReleaseDateFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Min release Date
                    item{
                        MinDateFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Max release Date
                    item{
                        MaxDateFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Min Rating Point
                    item{
                        MinRating(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Max Rating Point
                    item{
                        MaxRating(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Genre Filter
                    item {
                        GenreFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Without Genre Filter
                    item {
                        WithoutGenreFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Min Length Filter
                    item {
                        MinLength(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Max Length Filter
                    item {
                        MaxLength(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Origin Country Filter
                    item {
                        CountryFilter(viewModel = viewModel)
                    }
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    // Language Filter
                    item {
                        LanguageFilter(viewModel = viewModel)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .weight(0.2f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(35.dp)
                            .padding(start = 7.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ){
                        Button(
                            onClick = {
                                // Handle Cancel button action
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel_button),
                                fontFamily = Poppins
                            )
                        }
                    }


                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(35.dp)
                            .padding(end = 7.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ){
                        Button(
                            onClick = {
                                funcToCall()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.apply_button),
                                fontFamily = Poppins
                            )
                        }
                    }
                }
            }
        }
    )
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilterButton(
    funcToCall: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(true) }
    viewModel.resetUpdateFilter()

    AnimatedVisibility(
        visible = showDialog.value,
        enter = scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(durationMillis = 300)
        ) + fadeIn(),
        exit = scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut()
    ) {
        FilterDialog(funcToCall, onDismiss, viewModel)
    }
}


@Composable
fun ReleaseDateSort(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val releaseSort by remember { viewModel.releaseDateSort }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.reverseReleaseDateSort()
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_releasefilm),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 7.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.ReleaseDate_SortState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        when (releaseSort) {
            null -> {
                Icon(
                    painter = painterResource(R.drawable.ic_threedots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        )
                    ,
                    tint = Color.White
                )
            }
            Order.ASCENDING -> {
                Icon(
                    painter = painterResource(R.drawable.ic_ascending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        )
                    ,
                    tint =  Color.White
                )
            }
            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_decending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        )
                    ,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun RatingSort(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val ratingSort by remember { viewModel.ratingSort }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.reverseRatingSort()
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 9.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Rating_SortState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        when (ratingSort) {
            null -> {
                Icon(
                    painter = painterResource(R.drawable.ic_threedots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White
                )
            }
            Order.ASCENDING -> {
                Icon(
                    painter = painterResource(R.drawable.ic_ascending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White
                )
            }
            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_decending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        )
                    ,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun VoteSort(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val voteSort by remember { viewModel.voteSort }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.reverseVoteSort()
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_vote),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 8.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Vote_SortState),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        when (voteSort) {
            null -> {
                Icon(
                    painter = painterResource(R.drawable.ic_threedots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            Order.ASCENDING -> {
                Icon(
                    painter = painterResource(R.drawable.ic_ascending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_decending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun OriginalTitleSort(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val originalTSort by remember { viewModel.originalTitleSort }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.reverseOTitleSort()
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_title),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.OriginalTitle),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        when (originalTSort) {
            null -> {
                Icon(
                    painter = painterResource(R.drawable.ic_threedots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            Order.ASCENDING -> {
                Icon(
                    painter = painterResource(R.drawable.ic_ascending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_decending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
        }
    }

}

@Composable
fun TitleSort(
    viewModel: MovieListViewModel = hiltViewModel()
)
{
    val tilteSort by remember { viewModel.titleSort }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { viewModel.reverseTitleSort() }
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
    ) {
        // Icon Signature
        Icon(
            painterResource(id = R.drawable.ic_title),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 3.dp,
                    bottom = 5.dp,
                    start = 10.dp,
                    end = 9.dp
                )
            ,
            tint = Color.White
        )
        Text(
            text = stringResource(id = R.string.Title),
            fontSize = 21.sp,
            fontFamily = PoppinsBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        when (tilteSort) {
            null -> {
                Icon(
                    painter = painterResource(R.drawable.ic_threedots),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            Order.ASCENDING -> {
                Icon(
                    painter = painterResource(R.drawable.ic_ascending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_decending),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(
                            end = 15.dp
                        ),
                    tint = Color.White,
                )
            }
        }
    }

}


@Composable
fun SortDialog(
    funcToCall: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    viewModel.updateSortViewModel()
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true),
        content = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .background(MaterialTheme.colors.surface)
                    .fillMaxWidth()
                    .width(340.dp)
                    .height(450.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sort_title),
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontFamily = LobsterRegular,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .weight(0.2f)
                )

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .weight(1.4f)
                ) {
                    Row(
                        //horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = MaterialTheme.colors.primary)
                    ) {
                        Text(
                            text = "Reset Options",
                            textAlign = TextAlign.Start,
                            fontSize = 21.sp,
                            fontFamily = PoppinsBold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 26.dp)
                        )
                        Spacer(modifier = Modifier.weight(2f))

                        IconButton(
                            onClick = {
                                viewModel.resetAllSortDefault()
                                viewModel.updateSortViewModel()
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_backup), contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    //ReleaseDate Sort
                        ReleaseDateSort(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    //Rating Sort
                        RatingSort(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    //Vote Sort
                        VoteSort(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    //Original Tilte Sort
                        OriginalTitleSort(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                    //Tilte Sort
                        TitleSort(viewModel = viewModel)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .weight(0.2f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(35.dp)
                            .padding(start = 7.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ){
                        Button(
                            onClick = {
                                // Handle Cancel button action
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel_button),
                                fontFamily = Poppins
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(35.dp)
                            .padding(end = 7.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ){
                        Button(
                            onClick = {
                                funcToCall()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.apply_button),
                                fontFamily = Poppins
                            )
                        }
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SortButton(
    funcToCall: () -> Unit,
    onDismiss: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(true) }
    viewModel.resetUpdateSort()

    AnimatedVisibility(
        visible = showDialog.value,
        enter = scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(durationMillis = 300)
        ) + fadeIn(),
        exit = scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut()
    ) {
        SortDialog(funcToCall, onDismiss, viewModel)
    }
}

@Composable
fun ErrorDialog(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,

) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                showDialog.value = false
            },
            title = { Text(text = stringResource(id = R.string.error_label)) },
            text = {
                Text(text = error)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRetry()
                        showDialog.value = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.retry_button))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Handle Cancel button action
                        onDismiss()
                        showDialog.value = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
            }

        )
    }
}