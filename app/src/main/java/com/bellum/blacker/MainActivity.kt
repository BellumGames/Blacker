package com.bellum.blacker

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bellum.blacker.main.DisplayMusics
import com.bellum.blacker.ui.theme.BlackerTheme
import java.io.File
import com.bellum.blacker.main.Music

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ isGrandted: Boolean ->
            if(isGrandted){
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    companion object{
        var musicList: ArrayList<Music> = ArrayList()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestselfpermissionReadMediaAudio(){
        when{
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                musicList = getAllAudio()
                //Permission is granted
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_MEDIA_AUDIO
                )
                requestselfpermissionReadMediaAudio()
                //Additional rationale should be displayed
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_MEDIA_AUDIO
                )
                requestselfpermissionReadMediaAudio()
                //Permission has not been asked yet
            }
        }
    }

    @SuppressLint("Recycle", "Range")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getAllAudio():ArrayList<Music>{
        val musicList = ArrayList<Music>()
        val selection = Audio.Media.IS_MUSIC + " !=0"
        val  projection = arrayOf(
            Audio.Media._ID,
            Audio.Media.TITLE,
            Audio.Media.ALBUM,
            Audio.Media.ARTIST,
            Audio.Media.DURATION,
            Audio.Media.DATE_ADDED,
            Audio.Media.DATA
        )
        val cursor = this.contentResolver.query(Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, Audio.Media.DATE_ADDED + " DESC", null)
        if(cursor != null) {
            if(cursor.moveToFirst()){
                do{
                    val idT = cursor.getString(cursor.getColumnIndex(Audio.Media._ID))
                    val titleT = cursor.getString(cursor.getColumnIndex(Audio.Media.TITLE))
                    val albumT = cursor.getString(cursor.getColumnIndex(Audio.Media.ALBUM))
                    val artistT = cursor.getString(cursor.getColumnIndex(Audio.Media.ARTIST))
                    val durationT = cursor.getLong(cursor.getColumnIndex(Audio.Media.DURATION))
                    val pathT = cursor.getString(cursor.getColumnIndex(Audio.Media.DATA))
                    val musicT = Music(
                        id = idT,
                        title = titleT,
                        album = albumT,
                        artist = artistT,
                        duration = durationT,
                        path = pathT
                    )
                    if(File(musicT.path).exists()){
                        musicList.add(musicT)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return musicList
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    requestselfpermissionReadMediaAudio()
                    DisplayMusics(musicList)
                }
            }
        }
    }
}