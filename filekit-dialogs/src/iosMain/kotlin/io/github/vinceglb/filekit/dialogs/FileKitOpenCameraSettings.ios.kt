package io.github.vinceglb.filekit.dialogs

import platform.UIKit.UIViewController

/**
 * iOS implementation of [FileKitOpenCameraSettings].
 *
 * @property presenter The view controller used to present the camera picker.
 */
public actual class FileKitOpenCameraSettings(
    public val presenter: UIViewController? = null,
) {
    public actual companion object {
        /**
         * Creates a default instance of [FileKitOpenCameraSettings].
         */
        public actual fun createDefault(): FileKitOpenCameraSettings = FileKitOpenCameraSettings()
    }
}
