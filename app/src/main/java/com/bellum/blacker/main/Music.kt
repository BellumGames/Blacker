package com.bellum.blacker.main

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bellum.blacker.R

data class Music(val id:String, val title:String, val album:String, val artist:String, val duration: Long = 0, val path:String)

@Composable
fun DisplayMusic(music: Music)
{
    Row(modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
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
                    text = music.title,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState(), true)
            ) {
                Text(
                    text = music.artist,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
    }
}

@Composable
fun DisplayMusics(musicList: ArrayList<Music>)
{
    LazyVerticalGrid(
        GridCells.Fixed(1),
        content = {
            musicList.map { item { DisplayMusic(it) } }
        }
    )
}