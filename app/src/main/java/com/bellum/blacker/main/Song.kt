package com.bellum.blacker.main

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bellum.blacker.ui.theme.BlackerTheme

data class Song(val songTitle: String, val artistName: String, @DrawableRes val albumCover: Int)

@Composable
fun DisplaySong(song: Song)
{
    Row(modifier = Modifier) {
        Image(
            painter = painterResource(id = song.albumCover),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(0), true)
            ) {
                Text(
                    text = song.songTitle,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(), true)
            ) {
                Text(
                    text = song.artistName,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplaySongs(songList: List<Song>)
{
    LazyVerticalGrid(
        GridCells.Fixed(1),
        content = {
            songList.map { item { DisplaySong(it) } }
        }
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    BlackerTheme {
        val songList: MutableList<Song> = mutableListOf()
        for(i in 0..24)
        {
            songList.add(Song("Primo Victoria----------------------------------------------------------", "Sabaton", com.bellum.blacker.R.drawable.ic_launcher_background))
        }
        DisplaySongs(songList)
    }
}
