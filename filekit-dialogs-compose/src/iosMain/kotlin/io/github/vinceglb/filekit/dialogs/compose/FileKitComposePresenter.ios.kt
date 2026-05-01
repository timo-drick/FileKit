package io.github.vinceglb.filekit.dialogs.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitOpenCameraSettings
import io.github.vinceglb.filekit.dialogs.FileKitShareSettings

@Composable
internal actual fun rememberStableDialogSettings(
    dialogSettings: FileKitDialogSettings,
): FileKitDialogSettings {
    // Capture the presenter where the launcher is remembered, not where launch() is called.
    val presenter = dialogSettings.presenter ?: LocalUIViewController.current
    return remember(dialogSettings, presenter) {
        FileKitDialogSettings(
            title = dialogSettings.title,
            canCreateDirectories = dialogSettings.canCreateDirectories,
            assetRepresentationMode = dialogSettings.assetRepresentationMode,
            presenter = presenter,
        )
    }
}

@Composable
internal actual fun rememberStableOpenCameraSettings(
    openCameraSettings: FileKitOpenCameraSettings,
): FileKitOpenCameraSettings {
    // Capture the presenter where the launcher is remembered, not where launch() is called.
    val presenter = openCameraSettings.presenter ?: LocalUIViewController.current
    return remember(openCameraSettings, presenter) {
        FileKitOpenCameraSettings(presenter = presenter)
    }
}

@Composable
internal actual fun rememberStableShareSettings(
    shareSettings: FileKitShareSettings,
): FileKitShareSettings {
    // Capture the presenter where the launcher is remembered, not where launch() is called.
    val presenter = shareSettings.presenter ?: LocalUIViewController.current
    return remember(shareSettings, presenter) {
        FileKitShareSettings(
            metaTitle = shareSettings.metaTitle,
            addOptionUIActivityViewController = shareSettings.addOptionUIActivityViewController,
            presenter = presenter,
        )
    }
}
