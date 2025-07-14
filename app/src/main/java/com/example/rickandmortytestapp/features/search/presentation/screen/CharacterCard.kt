package com.example.rickandmortytestapp.features.search.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rickandmortytestapp.R
import com.example.rickandmortytestapp.features.search.domain.model.Character

@Composable
fun CharacterCard(
    character: Character?,
    navigateToDetailScreen: (Long) -> Unit,
) {
    val status by remember(character) {
        derivedStateOf { character?.status ?: "unknown" }
    }

    if (character == null) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.3f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(percent = 6),
        ) {
            Text("Loading...")
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clickable { navigateToDetailScreen(character.id) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(percent = 12),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier.height(160.dp),
                        model = character.image,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.error),
                        contentScale = ContentScale.Crop,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(topStart = 16.dp))
                            .align(Alignment.BottomEnd)
                            .background(Color.Black),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val color = when (status) {
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
                            text = character.status ?: "unknown",
                            color = Color.LightGray,
                            maxLines = 1,
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        text = character.name ?: "unknown",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray,
                            text = character.gender ?: "unknown",
                            maxLines = 1,
                            textAlign = TextAlign.End,
                        )
                        VerticalDivider(
                            thickness = 4.dp,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray,
                            text = character.species ?: "unknown",
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                        )
                    }
                }

            }
        }
    }
}