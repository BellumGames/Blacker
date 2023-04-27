package com.bellum.blacker.main

import android.provider.MediaStore
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bellum.blacker.MainActivity
import com.bellum.blacker.R

data class MusicAlt(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String
)

fun getAllMP3Alt(mainActivity: MainActivity): List<MusicAlt> {
    val musicList = ArrayList<MusicAlt>()
    val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )
    val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"

    // Retrieve MP3 files from internal storage
    val internalMusicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
    val internalCursor = mainActivity.contentResolver.query(
        internalMusicUri, projection, selection, null, sortOrder
    )
    if (internalCursor != null && internalCursor.moveToFirst()) {
        do {
            val id = internalCursor.getString(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            val title = internalCursor.getString(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            val album = internalCursor.getString(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
            val artist = internalCursor.getString(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
            val duration = internalCursor.getLong(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
            val path = internalCursor.getString(internalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
            musicList.add(MusicAlt(id, title, album, artist, duration, path))
        } while (internalCursor.moveToNext())
        internalCursor.close()
    }

    // Retrieve MP3 files from external storage
    val externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val externalCursor = mainActivity.contentResolver.query(
        externalMusicUri, projection, selection, null, sortOrder
    )
    if (externalCursor != null && externalCursor.moveToFirst()) {
        do {
            val id = externalCursor.getString(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            val title = externalCursor.getString(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
            val album = externalCursor.getString(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
            val artist = externalCursor.getString(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
            val duration = externalCursor.getLong(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
            val path = externalCursor.getString(externalCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
            musicList.add(MusicAlt(id, title, album, artist, duration, path))
        } while (externalCursor.moveToNext())
        externalCursor.close()
    }
    return musicList
}

@Composable
fun DisplayMusicAlt(music: MusicAlt)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayMusics(musicList: List<MusicAlt>)
{
    LazyVerticalGrid(
        GridCells.Fixed(1),
        content = {
            musicList.map { item { DisplayMusicAlt(it) } }
        }
    )
}
