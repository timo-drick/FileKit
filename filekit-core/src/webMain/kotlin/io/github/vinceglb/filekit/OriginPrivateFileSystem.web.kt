package io.github.vinceglb.filekit

import io.github.vinceglb.filekit.exceptions.FileKitException
import kotlinx.coroutines.await
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.JsName
import kotlin.js.Promise

/**
 * Reads the root directory of the browser's origin private file system (OPFS).
 *
 * The returned [PlatformFile] retains a live OPFS directory handle. Use
 * [listOriginPrivateFileSystemEntries], [originPrivateFileSystemFile], and
 * [originPrivateFileSystemDirectory] to interact with its entries
 * asynchronously.
 *
 * OPFS is available only in secure contexts in browsers that implement the File
 * System API.
 */
@OptIn(ExperimentalWasmJsInterop::class)
public suspend fun PlatformFile.Companion.fromOriginPrivateFileSystem(): PlatformFile {
    val root = browserNavigator.storage.getDirectory().await()
    return PlatformFile(
        WebFile.OriginPrivateDirectory(
            handle = root,
            path = "",
            parent = null,
        ),
    )
}

@OptIn(ExperimentalWasmJsInterop::class)
public suspend fun PlatformFile.listOriginPrivateFileSystemEntries(): List<PlatformFile> {
    val directory = webFile as? WebFile.OriginPrivateDirectory
        ?: throw FileKitException("This file is not an origin private file system directory")
    val iterator = directory.handle.values()
    val entries = mutableListOf<PlatformFile>()

    while (true) {
        val entry = iterator.next().await()
        if (entry.done) break

        val handle = entry.value
            ?: throw FileKitException("Could not read origin private file system entry")
        val path = directory.path.appendOriginPrivateFileSystemPath(handle.name)
        entries += PlatformFile(
            when (handle.kind) {
                "file" -> WebFile.OriginPrivateFile(
                    handle = handle.unsafeCast<FileSystemFileHandle>(),
                    path = path,
                    parent = directory,
                )

                "directory" -> WebFile.OriginPrivateDirectory(
                    handle = handle.unsafeCast<FileSystemDirectoryHandle>(),
                    path = path,
                    parent = directory,
                )

                else -> throw FileKitException("Unsupported origin private file system entry type: ${handle.kind}")
            },
        )
    }

    return entries
}

/** Returns an OPFS file in this directory, creating it when [create] is true. */
@OptIn(ExperimentalWasmJsInterop::class)
public suspend fun PlatformFile.originPrivateFileSystemFile(
    name: String,
    create: Boolean = false,
): PlatformFile {
    val directory = webFile as? WebFile.OriginPrivateDirectory
        ?: throw FileKitException("This file is not an origin private file system directory")
    return PlatformFile(
        WebFile.OriginPrivateFile(
            handle = directory.handle.getFileHandle(name, FileSystemGetHandleOptions(create)).await(),
            path = directory.path.appendOriginPrivateFileSystemPath(name),
            parent = directory,
        ),
    )
}

/** Returns an OPFS directory in this directory, creating it when [create] is true. */
@OptIn(ExperimentalWasmJsInterop::class)
public suspend fun PlatformFile.originPrivateFileSystemDirectory(
    name: String,
    create: Boolean = false,
): PlatformFile {
    val directory = webFile as? WebFile.OriginPrivateDirectory
        ?: throw FileKitException("This file is not an origin private file system directory")
    return PlatformFile(
        WebFile.OriginPrivateDirectory(
            handle = directory.handle.getDirectoryHandle(name, FileSystemGetHandleOptions(create)).await(),
            path = directory.path.appendOriginPrivateFileSystemPath(name),
            parent = directory,
        ),
    )
}

/** Replaces the contents of this live OPFS file with [bytes]. */
@OptIn(ExperimentalWasmJsInterop::class)
public suspend fun PlatformFile.writeToOriginPrivateFileSystem(bytes: ByteArray) {
    val file = webFile as? WebFile.OriginPrivateFile
        ?: throw FileKitException("This file is not a writable origin private file system file")
    val writable = file.handle.createWritable().await()
    try {
        writable.write(bytes.toWebBytes()).await()
    } finally {
        writable.close().await()
    }
}

public actual suspend fun FileKit.filesDirectory(): PlatformFile =
    PlatformFile.fromOriginPrivateFileSystem()

public actual suspend fun FileKit.cacheDirectory(): PlatformFile =
    filesDirectory().originPrivateFileSystemDirectory(name = "cache", create = true)

public actual suspend fun FileKit.databasesDirectory(): PlatformFile =
    filesDirectory().originPrivateFileSystemDirectory(name = "databases", create = true)

internal fun String.appendOriginPrivateFileSystemPath(child: String): String =
    if (isEmpty()) child else "$this/$child"

@OptIn(ExperimentalWasmJsInterop::class)
@JsName("navigator")
private external val browserNavigator: BrowserNavigator

@OptIn(ExperimentalWasmJsInterop::class)
private external interface BrowserNavigator : JsAny {
    val storage: BrowserStorageManager
}

@OptIn(ExperimentalWasmJsInterop::class)
private external interface BrowserStorageManager : JsAny {
    fun getDirectory(): Promise<FileSystemDirectoryHandle>
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemHandle : JsAny {
    val kind: String
    val name: String
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemFileHandle : FileSystemHandle {
    fun getFile(): Promise<BrowserFile>
    fun createWritable(): Promise<FileSystemWritableFileStream>
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemDirectoryHandle : FileSystemHandle {
    fun values(): FileSystemDirectoryHandleIterator
    fun getFileHandle(name: String, options: FileSystemGetHandleOptions): Promise<FileSystemFileHandle>
    fun getDirectoryHandle(name: String, options: FileSystemGetHandleOptions): Promise<FileSystemDirectoryHandle>
    fun removeEntry(name: String): Promise<JsAny?>
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemDirectoryHandleIterator : JsAny {
    fun next(): Promise<FileSystemDirectoryHandleIteratorResult>
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemDirectoryHandleIteratorResult : JsAny {
    val done: Boolean
    val value: FileSystemHandle?
}

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemGetHandleOptions : JsAny {
    var create: Boolean
}

@OptIn(ExperimentalWasmJsInterop::class)
internal fun FileSystemGetHandleOptions(create: Boolean): FileSystemGetHandleOptions =
    js("({ create })")

@OptIn(ExperimentalWasmJsInterop::class)
internal external interface FileSystemWritableFileStream : JsAny {
    fun write(data: JsAny): Promise<JsAny?>
    fun close(): Promise<JsAny?>
}

@OptIn(ExperimentalWasmJsInterop::class)
internal expect fun ByteArray.toWebBytes(): JsAny
