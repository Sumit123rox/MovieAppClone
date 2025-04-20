package com.sumit.movieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.sumit.movieapp.R
import com.sumit.movieapp.domain.Movie
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .padding(8.sdp)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = onClick
            )
            .shadow(elevation = 4.sdp, shape = RoundedCornerShape(12.sdp)),
        shape = RoundedCornerShape(12.sdp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f),
                placeholder = painterResource(R.drawable.dummy_movie),
                error = painterResource(R.drawable.dummy_movie)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.sdp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
