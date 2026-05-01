package io.github.vinceglb.filekit.dialogs.compose

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitOpenCameraSettings
import io.github.vinceglb.filekit.dialogs.FileKitShareSettings

@Composable
internal actual fun rememberStableDialogSettings(
    dialogSettings: FileKitDialogSettings,
): FileKitDialogSettings = dialogSettings

@Composable
internal actual fun rememberStableOpenCameraSettings(
    openCameraSettings: FileKitOpenCameraSettings,
): FileKitOpenCameraSettings = openCameraSettings

@Composable
internal actual fun rememberStableShareSettings(
    shareSettings: FileKitShareSettings,
): FileKitShareSettings = shareSettings
