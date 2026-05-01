package io.github.vinceglb.filekit.dialogs.compose

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings

@Composable
internal expect fun rememberStableDialogSettings(
    dialogSettings: FileKitDialogSettings,
): FileKitDialogSettings
