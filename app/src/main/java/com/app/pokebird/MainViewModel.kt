package com.app.pokebird

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(context: Context, bitmap: Bitmap) {
        val formatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val timestamp = formatter.format(Date())
        val filename = "photo_${timestamp}.png"
        if (saveBitmapToInternalStorage(context, bitmap, filename)) {
            _bitmaps.value += bitmap
        }
    }

    fun loadPhotos(context: Context) {
        val loadedBitmaps = mutableListOf<Bitmap>()
        for (fileName in context.fileList()) {
            if (fileName.endsWith(".png")) {
                try {
                    context.openFileInput(fileName).use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)?.let {
                            loadedBitmaps.add(it)
                        }
                    }
                } catch (e: IOException) {
                    Log.e("PhotoLoad", "Error loading photo: $fileName", e)
                }
            }
        }
        _bitmaps.value = loadedBitmaps
    }

    private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, filename: String): Boolean {
        return try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            true
        } catch (e: IOException) {
            Log.e("PhotoSave", "Error saving photo: $filename", e)
            false
        }
    }
}
