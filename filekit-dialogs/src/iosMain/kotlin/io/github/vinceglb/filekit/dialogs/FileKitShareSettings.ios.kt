package io.github.vinceglb.filekit.dialogs

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIViewController

/**
 * iOS implementation of [FileKitShareSettings].
 *
 * @property metaTitle The title of the share sheet. Defaults to "Share File".
 * @property addOptionUIActivityViewController Callback to customize the [UIActivityViewController].
 * @property presenter The view controller used to present the share sheet.
 */
public actual class FileKitShareSettings(
    public val metaTitle: String = "Share File",
    public val addOptionUIActivityViewController: (UIActivityViewController) -> Unit = {},
    public val presenter: UIViewController? = null,
) {
    public actual companion object {
        /**
         * Creates a default instance of [FileKitShareSettings].
         */
        public actual fun createDefault(): FileKitShareSettings = FileKitShareSettings()
    }
}
