package com.example.kotlin_practice_8_10.screens

import android.content.Context
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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.kotlin_practice_8_10.workmanager.ImageDownloadWorker

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
                val inputData = workDataOf("imageUrl" to link)

                val imageDownloadRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                    .setInputData(inputData)
                    .build()

                WorkManager.getInstance(context).enqueue(imageDownloadRequest)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "download picture")
        }
    }
}
