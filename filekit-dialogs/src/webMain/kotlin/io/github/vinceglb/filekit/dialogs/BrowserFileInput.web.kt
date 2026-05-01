package io.github.vinceglb.filekit.dialogs

import io.github.vinceglb.filekit.BrowserFile
import io.github.vinceglb.filekit.WebFile
import kotlinx.browser.document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import org.w3c.files.FileList
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalWasmJsInterop::class)
internal suspend fun openBrowserFileInput(
    type: FileKitType,
    multipleMode: Boolean,
    directoryMode: Boolean,
): List<WebFile.FileWrapper>? = withContext(Dispatchers.Default) {
    suspendCancellableCoroutine { continuation ->
        val input = document.createElement("input") as BrowserFileInputElement
        input.style.display = "none"
        document.body?.appendChild(input)

        input.apply {
            this.type = "file"
            accept = type.acceptAttribute
            multiple = multipleMode
            webkitdirectory = directoryMode
        }

        input.onchange = { event ->
            try {
                val result = event.target
                    ?.unsafeCast<BrowserFileInputElement>()
                    ?.files
                    ?.asList()
                    ?.map { WebFile.FileWrapper(it.unsafeCast<BrowserFile>()) }

                continuation.resume(result)
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            } finally {
                document.body?.removeChild(input)
            }
        }

        input.oncancel = {
            continuation.resume(null)
            document.body?.removeChild(input)
        }

        input.click()
    }
}

private val FileKitType.acceptAttribute: String
    get() = when (this) {
        is FileKitType.Image -> {
            "image/*"
        }

        is FileKitType.Video -> {
            "video/*"
        }

        is FileKitType.ImageAndVideo -> {
            "image/*,video/*"
        }

        is FileKitType.File -> {
            extensions
                ?.joinToString(",") { ".$it" }
                .orEmpty()
        }
    }

@JsName("HTMLInputElement")
internal abstract external class BrowserFileInputElement : HTMLElement {
    open var accept: String
    open val files: FileList?
    open var multiple: Boolean
    open var webkitdirectory: Boolean
    open var type: String
    open var value: String
}
