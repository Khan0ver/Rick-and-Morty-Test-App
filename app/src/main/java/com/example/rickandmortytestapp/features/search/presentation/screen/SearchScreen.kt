@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickandmortytestapp.features.search.presentation.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.rickandmortytestapp.R
import com.example.rickandmortytestapp.features.search.presentation.model.FilterInfo
import com.example.rickandmortytestapp.features.search.presentation.navigation.Detail
import com.example.rickandmortytestapp.features.search.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController) {
    val searchViewModel = hiltViewModel<SearchViewModel>()

    SearchScreen(
        viewModel = searchViewModel,
        navController = navController
    )
}

@Composable
private fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    val filterInfo = viewModel.filter.collectAsState()
    viewModel.getCharactersWithFilter()

    SearchScreen(
        viewModel = viewModel,
        filterInfo = filterInfo,
        onNameChanged = { viewModel.changeName(it) },
        onStatusChanged = { viewModel.changeStatus(it) },
        onSpeciesChanged = { viewModel.changeSpecies(it) },
        onTypeChanged = { viewModel.changeType(it) },
        onGenderChanged = { viewModel.changeGender(it) },
        pressedOnTheCharacter = { navController.navigate(Detail(id = it)) },
        searchFilteredCharacters = { viewModel.getCharactersWithFilter() }
    )
}

@Composable
private fun SearchScreen(
    viewModel: SearchViewModel,
    filterInfo: State<FilterInfo>,
    onNameChanged: (String) -> Unit,
    onStatusChanged: (String) -> Unit,
    onSpeciesChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    pressedOnTheCharacter: (Long) -> Unit,
    searchFilteredCharacters: () -> Unit,
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()
    var isTriedGetRemoteData by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    val showFilters = remember { mutableStateOf(false) }

    val messageSnackbarNoInternet = stringResource(R.string.no_internet)
    val messageSnackbarError = stringResource(R.string.something_wrong)

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarForSearch(
                        filterInfo = filterInfo,
                        onChangeShowFilters = {
                            showFilters.value = !showFilters.value
                        },
                        onChangeName = {
                            onNameChanged(it)
                        },
                        onSearchWithFiltered = {
                            searchFilteredCharacters()
                            characters.refresh()
                        },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FilterChoose(
                showFilters = showFilters,
                filterInfo = filterInfo,
                onChangeStatus = { onStatusChanged(it) },
                onChangeSpecies = { onSpeciesChanged(it) },
                onChangeType = { onTypeChanged(it) },
                onChangeGender = { onGenderChanged(it) },
            )

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                    if (isInternetAvailable(context)) {
                        characters.refresh()
                    } else {
                        scope.launch { snackbarHostState.showSnackbar(messageSnackbarNoInternet) }
                    }
                    isRefreshing = false
                },
                modifier = Modifier
                    .fillMaxSize(),
                state = pullRefreshState,
            ) {

                if (characters.itemCount != 0) {

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            count = characters.itemCount,
                            key = characters.itemKey { it.id },
                            contentType = characters.itemContentType { "MyPagingItems" },
                        ) { index ->
                            CharacterCard(
                                character = characters[index],
                                pressedOnTheCharacter
                            )
                        }
                    }

                } else {

                    if (!isInternetAvailable(context)) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(R.string.no_internet)
                            )
                        }
                    } else {
                        if (!isTriedGetRemoteData) {
                            characters.refresh()
                            isTriedGetRemoteData = true
                        } else {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.no_founded_characters)
                                )
                            }
                        }
                    }

                }

                if (
                    characters.loadState.refresh is LoadState.Loading ||
                    characters.loadState.append is LoadState.Loading ||
                    isRefreshing
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .align(Alignment.TopCenter)
                    )
                }

                LaunchedEffect(
                    characters
                ) {
                    if (
                        characters.loadState.refresh is LoadState.Error ||
                        characters.loadState.append is LoadState.Error
                    ) {
                        scope.launch { snackbarHostState.showSnackbar(messageSnackbarError) }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChoose(
    showFilters: MutableState<Boolean>,
    filterInfo: State<FilterInfo>,
    onChangeStatus: (String) -> Unit,
    onChangeSpecies: (String) -> Unit,
    onChangeType: (String) -> Unit,
    onChangeGender: (String) -> Unit,
) {
    val listOfStatus = listOf(
        Pair(stringResource(R.string.status_any), ""),
        Pair(stringResource(R.string.status_alive), "alive"),
        Pair(stringResource(R.string.status_dead), "dead"),
        Pair(stringResource(R.string.status_unknown), "unknown"),
    )
    val listOfGender = listOf(
        Pair(stringResource(R.string.gender_any), ""),
        Pair(stringResource(R.string.gender_male), "male"),
        Pair(stringResource(R.string.gender_female), "female"),
        Pair(stringResource(R.string.gender_genderless), "genderless"),
        Pair(stringResource(R.string.gender_unknown), "unknown"),
    )

    AnimatedVisibility(
        visible = showFilters.value,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilterDropDownMenu(
                string = filterInfo.value.status,
                listOfFilter = listOfStatus,
                onChangeValue = onChangeStatus,
                textForDropdownMenu = stringResource(R.string.status_choose)
            )
            FilterDropDownMenu(
                string = filterInfo.value.gender,
                listOfFilter = listOfGender,
                onChangeValue = onChangeGender,
                textForDropdownMenu = stringResource(R.string.gender_choose)
            )
            TextFieldForFilter(
                filterInfo = filterInfo,
                onValueChanged = onChangeSpecies,
                textForPlaceholder = stringResource(R.string.character_species),
                textForLabel = stringResource(R.string.character_species),
            )
            TextFieldForFilter(
                filterInfo = filterInfo,
                onValueChanged = onChangeType,
                textForPlaceholder = stringResource(R.string.character_type),
                textForLabel = stringResource(R.string.character_type),
            )
        }
    }
}

@Composable
fun TextFieldForFilter(
    filterInfo: State<FilterInfo>,
    onValueChanged: (String) -> Unit,
    textForPlaceholder: String,
    textForLabel: String,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = filterInfo.value.type,
        onValueChange = {
            onValueChanged(it)
        },
        placeholder = {
            Text(textForPlaceholder)
        },
        label = {
            Text(textForLabel)
        },
    )
}

@Composable
fun FilterDropDownMenu(
    string: String,
    listOfFilter: List<Pair<String, String>>,
    onChangeValue: (String) -> Unit,
    textForDropdownMenu: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var expanded by remember { mutableStateOf(false) }
        Text(
            text = textForDropdownMenu,
            textAlign = TextAlign.Center,
        )
        TextButton(
            onClick = {
                expanded = true
            },
        ) {
            Text(if (string != "") string else listOfFilter[0].first)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded }
            ) {
                listOfFilter.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.first)
                        },
                        onClick = {
                            onChangeValue(it.second)
                            expanded = false
                        })
                }
            }
        }
    }
}

@Composable
fun TopAppBarForSearch(
    filterInfo: State<FilterInfo>,
    onChangeShowFilters: () -> Unit,
    onChangeName: (String) -> Unit,
    onSearchWithFiltered: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.75f),
                value = filterInfo.value.name,
                onValueChange = {
                    onChangeName(it)
                },
                placeholder = {
                    Text("Search character by name", textAlign = TextAlign.Center)
                },
                label = {
                    Text("Character name")
                },
            )
            IconButton(
                onClick = {
                    onSearchWithFiltered()
                }
            ) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            }
            IconButton(
                onClick = {
                    onChangeShowFilters()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    val result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}