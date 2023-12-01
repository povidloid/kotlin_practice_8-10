package com.example.kotlin_practice_8_10.workmanager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class ImageDownloadWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        lateinit var folder : File
        return try {
            val imageUrl = inputData.getString("imageUrl")
            try {
                val path = applicationContext.getExternalFilesDir(null)
                folder = File(path, "photos")
                folder.mkdirs()
            }catch(e: Exception){
                Log.e("MyApp", e.toString())
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL(imageUrl)
                    val connection = url.openConnection()
                    connection.doInput = true
                    connection.connect()
                    val input = connection.getInputStream()
                    val bitmap = BitmapFactory.decodeStream(input)

                    val fileName = "my_image.jpg"
                    val file = File(folder, fileName)
                    file.createNewFile()
                    val fileOutputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()

                    MainScope().launch { Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show() }

                } catch (e: Exception) {
                    Log.e("MyApp", e.toString())
                }
            }
            Result.success()
        } catch (e: Exception) {
            Thread{Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()}.start()
            Log.e("MyApp", "Error downloading image: $e")
            Result.failure()
        }
    }

    private fun loadImageSynchronously(imageUrl: String?): Bitmap {
        return Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .submit()
            .get()
    }

    private fun saveBitmapToFile(bitmap: Bitmap) {
        try {
            val path = applicationContext.getExternalFilesDir(null)
            val folder = File(path, "photos")
            folder.mkdirs()

            val fileName = "my_image.jpg"
            val file = File(folder, fileName)
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            Log.d("MyApp", "saveBitmapToFile: $path")
        } catch (e: Exception) {
            Log.e("MyApp", "Error saving image: $e")
        }
    }
}