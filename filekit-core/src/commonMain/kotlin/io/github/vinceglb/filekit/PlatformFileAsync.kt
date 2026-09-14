package io.github.vinceglb.filekit

import io.github.vinceglb.filekit.mimeType.MimeType
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/** Lists this directory's current children. */
public expect suspend fun PlatformFile.listAsync(): List<PlatformFile>

/** Returns a child file, creating it when [create] is true. */
public expect suspend fun PlatformFile.file(
    name: String,
    create: Boolean = false,
): PlatformFile

/** Returns a child directory, creating it when [create] is true. */
public expect suspend fun PlatformFile.directory(
    name: String,
    create: Boolean = false,
): PlatformFile

/** Replaces this file's content with [bytes]. */
public expect suspend fun PlatformFile.writeBytes(bytes: ByteArray)

/** Replaces this file's content with [string]. */
public suspend fun PlatformFile.writeStringAsync(string: String) {
    writeBytes(string.encodeToByteArray())
}

/** Deletes this file or empty directory. */
public expect suspend fun PlatformFile.deleteAsync(mustExist: Boolean = true)

/** Returns whether this file currently exists. */
public expect suspend fun PlatformFile.existsAsync(): Boolean

/** Returns this file's current size in bytes. */
public expect suspend fun PlatformFile.sizeAsync(): Long

/** Returns this file's current MIME type, if available. */
public expect suspend fun PlatformFile.mimeTypeAsync(): MimeType?

/** Returns this file's current modification time. */
@OptIn(ExperimentalTime::class)
public expect suspend fun PlatformFile.lastModifiedAsync(): Instant
