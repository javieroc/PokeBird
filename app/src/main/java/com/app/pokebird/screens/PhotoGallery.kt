package com.app.pokebird.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.app.pokebird.MainViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGallery(
    viewModel: MainViewModel,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToImageDetail: (fileName: String) -> Unit,
) {
    val context = LocalContext.current
    val fileNames by viewModel.fileNames.collectAsState()

    Surface(
        color = Color(0xff414548),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onNavigateToMainScreen() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xffff4554)
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
            ) {
                items(fileNames) { fileName ->
                    val imageFile = File(context.filesDir, fileName)
                    AsyncImage(
                        model = imageFile,
                        contentDescription = null,
                        modifier = Modifier.clickable { onNavigateToImageDetail(fileName) }
                    )
                }
            }
        }
    }
}