package com.example.kotlin_practice_8_10.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun thirdScreen() {
    var link by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = link,
            onValueChange = { link = it },
            label = { Text(text = "URL: ") }
        )
        Button(
            onClick = {
                downloadImage(context, link, "downloadedPhoto1")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "download picture")
        }
    }
}

fun downloadImage(context: Context, imageUrl: String, fileName: String) {
    Thread {
        try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap: Bitmap = BitmapFactory.decodeStream(input)
            saveImageToInternalStorage(context, bitmap, fileName)
            MainScope().launch { Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show() }
        } catch (e: IOException) {
            Log.d("MyApp", e.toString())
        }
    }.start()
}

private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, fileName: String) {
    val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppImages")
    if (!directory.exists()) {
        directory.mkdirs()
    }
    val file = File(directory, fileName)
    try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
        MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
    } catch (e: IOException) {
        Log.d("MyApp", e.toString())
    }
}
