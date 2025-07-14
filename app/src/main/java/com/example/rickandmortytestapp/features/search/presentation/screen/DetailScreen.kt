@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickandmortytestapp.features.search.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.rickandmortytestapp.R
import com.example.rickandmortytestapp.features.search.domain.model.Character
import com.example.rickandmortytestapp.features.search.presentation.viewmodel.SearchViewModel


@Composable
fun DetailScreen(navController: NavController, id: Long) {
    val viewModel = hiltViewModel<SearchViewModel>()
    viewModel.getCharacterInfoById(id)

    DetailScreen(
        viewModel,
        navController,
    )
}

@Composable
private fun DetailScreen(
    viewModel: SearchViewModel,
    navController: NavController,
) {
    DetailScreen(
        viewModel = viewModel,
        onBackPressed = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun DetailScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
) {
    val character = viewModel.character.collectAsState(Character(0))

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = character.value?.name ?: "Character Details"
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        model = character.value?.image,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.error),
                        contentScale = ContentScale.Crop,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .clip(RoundedCornerShape(topStart = 16.dp))
                            .align(Alignment.BottomEnd)
                            .background(Color.Black),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val color = when (character.value?.status) {
                            "Alive" -> Color.Green
                            "Dead" -> Color.DarkGray
                            else -> Color.LightGray
                        }

                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = character.value?.status ?: "unknown",
                            color = Color.LightGray,
                            maxLines = 1,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.15f)
                            .align(Alignment.TopEnd)
                            .background(Color.Black),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = character.value?.id.toString() + " Id",
                            color = Color.LightGray,
                            maxLines = 1,
                        )
                    }
                }

                RowWithInfo(
                    fieldName = stringResource(R.string.name) + ":",
                    infoForField = character.value?.name ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Status:",
                    infoForField = character.value?.status ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Species: ",
                    infoForField = character.value?.species ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Type:",
                    infoForField = character.value?.type ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Gender: ",
                    infoForField = character.value?.gender ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Current: ",
                    infoForField = character.value?.currentLocationName ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Origin: ",
                    infoForField = character.value?.originLocationName ?: "unknown",
                )

                RowWithInfo(
                    fieldName = "Created: ",
                    infoForField = character.value?.created ?: "unknown"
                )

                RowWithInfo(
                    fieldName = "URL: ",
                    infoForField = character.value?.url ?: "unknown"
                )
            }
        }
    }
}

@Composable
fun RowWithInfo(
    fieldName: String,
    infoForField: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            text = fieldName,
            textAlign = TextAlign.Start
        )

        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            text = infoForField,
            textAlign = TextAlign.Center
        )
    }

    HorizontalDivider(
        thickness = 2.dp
    )
}