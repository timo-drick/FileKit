package io.github.vinceglb.filekit.dialogs.compose

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.dialogs.FileKitOpenCameraSettings
import io.github.vinceglb.filekit.dialogs.FileKitShareSettings

@Composable
internal expect fun rememberStableOpenCameraSettings(
    openCameraSettings: FileKitOpenCameraSettings,
): FileKitOpenCameraSettings

@Composable
internal expect fun rememberStableShareSettings(
    shareSettings: FileKitShareSettings,
): FileKitShareSettings
