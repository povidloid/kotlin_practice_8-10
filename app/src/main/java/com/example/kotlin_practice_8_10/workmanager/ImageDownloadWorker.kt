package com.example.kotlin_practice_8_10.workmanager

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream

class ImageDownloadWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        return try {
            val imageUrl = inputData.getString("imageUrl")
            val bitmap = loadImageSynchronously(imageUrl)
            saveBitmapToFile(bitmap)
            Log.d("MyApp", "Success")
            Result.success()
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            Log.e("MyApp", "Error saving image: $e")
        }
    }
}