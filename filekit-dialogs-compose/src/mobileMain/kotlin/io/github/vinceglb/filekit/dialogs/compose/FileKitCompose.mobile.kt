package io.github.vinceglb.filekit.dialogs.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitOpenCameraSettings
import io.github.vinceglb.filekit.dialogs.FileKitShareSettings
import io.github.vinceglb.filekit.dialogs.shareFile
import kotlinx.coroutines.launch

@Composable
public expect fun rememberCameraPickerLauncher(
    openCameraSettings: FileKitOpenCameraSettings = FileKitOpenCameraSettings.createDefault(),
    onResult: (PlatformFile?) -> Unit,
): PhotoResultLauncher

@Composable
public fun rememberShareFileLauncher(
    shareSettings: FileKitShareSettings = FileKitShareSettings.createDefault(),
): ShareResultLauncher {
    // Coroutine
    val coroutineScope = rememberCoroutineScope()
    val stableShareSettings = rememberStableShareSettings(shareSettings)
    val currentShareSettings by rememberUpdatedState(stableShareSettings)

    // FileKit
    val fileKit = remember { FileKit }

    // FileKit launcher
    val returnedLauncher = remember {
        ShareResultLauncher { files ->
            coroutineScope.launch {
                fileKit.shareFile(files, currentShareSettings)
            }
        }
    }

    return returnedLauncher
}
