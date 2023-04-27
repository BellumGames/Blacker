package com.bellum.blacker.main

import android.annotation.SuppressLint
import android.os.Build
import android.provider.MediaStore.Audio.Media
import androidx.annotation.RequiresApi
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
import java.io.File

data class Music(val id:String, val title:String, val album:String, val artist:String, val duration: Long = 0, val path: String)

@SuppressLint("Recycle", "Range")
@RequiresApi(Build.VERSION_CODES.Q)
fun getAllMP3(mainActivity: MainActivity): List<Music> {
    val musicList = ArrayList<Music>()
    val selection = Media.IS_MUSIC + " !=0"
    val projection = arrayOf(Media._ID, Media.TITLE, Media.ALBUM, Media.ARTIST, Media.DURATION, Media.DATE_ADDED, Media.DATA)
    val cursor = mainActivity.contentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, selection, null, Media.DATE_ADDED + " DESC", null)
    if(cursor != null){
        if(cursor.moveToFirst()){
            do {
                val idT = cursor.getString(cursor.getColumnIndex(Media._ID))
                val titleT = cursor.getString(cursor.getColumnIndex(Media.TITLE))
                val albumT = cursor.getString(cursor.getColumnIndex(Media.ALBUM))
                val artistT = cursor.getString(cursor.getColumnIndex(Media.ARTIST))
                val duration = cursor.getLong(cursor.getColumnIndex(Media.DURATION))
                val pathT = cursor.getString(cursor.getColumnIndex(Media.DATA))
                val musicT = Music(idT, titleT, albumT, artistT, duration, pathT)
                if(File(musicT.path).exists()){
                    musicList.add(musicT)
                }
            }while (cursor.moveToNext())
        }
        cursor.close()
    }
    return musicList
}

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayMusics(musicList: List<Music>)
{
    LazyVerticalGrid(
        GridCells.Fixed(1),
        content = {
            musicList.map { item { DisplayMusic(it) } }
        }
    )
}
