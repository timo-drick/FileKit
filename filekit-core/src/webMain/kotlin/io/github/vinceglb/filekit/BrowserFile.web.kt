package io.github.vinceglb.filekit

import org.w3c.files.Blob
import org.w3c.files.FilePropertyBag
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.JsName
import kotlin.js.JsNumber
import kotlin.js.definedExternally

/**
 * External declaration for the browser's native File object.
 *
 * This includes web-specific metadata such as [webkitRelativePath], which is
 * used to rebuild virtual directory trees from directory picker results.
 */
@OptIn(ExperimentalWasmJsInterop::class)
@JsName("File")
public open external class BrowserFile(
    fileBits: JsArray<JsAny?>,
    fileName: String,
    options: FilePropertyBag = definedExternally,
) : Blob,
    JsAny {
    public val name: String
    public val lastModified: JsNumber
    public val webkitRelativePath: String?
}
