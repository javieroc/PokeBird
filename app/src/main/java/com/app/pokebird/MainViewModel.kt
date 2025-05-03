package com.app.pokebird

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel: ViewModel() {
    private val _fileNames = MutableStateFlow<List<String>>(emptyList())
    val fileNames = _fileNames.asStateFlow()
    private val _lastCapturedPhoto = MutableStateFlow<Bitmap?>(null)
    val lastCapturedPhoto = _lastCapturedPhoto.asStateFlow()

    fun onTakePhoto(context: Context, bitmap: Bitmap) {
        val formatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val timestamp = formatter.format(Date())
        val filename = "photo_${timestamp}.png"
        if (saveBitmapToInternalStorage(context, bitmap, filename)) {
            _lastCapturedPhoto.value = bitmap
            loadPhotoFileNames(context)
        }
    }

    fun clearLastCapturedPhoto() {
        _lastCapturedPhoto.value = null
    }

    fun loadPhotoFileNames(context: Context) {
        val photoNames = context.fileList().filter { it.endsWith(".png") }
        _fileNames.value = photoNames
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

    fun deletePhotoFile(context: Context, fileName: String): Boolean {
        val deleted = context.deleteFile(fileName)
        if (deleted) {
            loadPhotoFileNames(context)
        }
        return deleted
    }
}
