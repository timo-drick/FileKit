package io.github.vinceglb.filekit

public expect object FileKit

/**
 * Returns the directory for persistent application files.
 *
 * On Android, this corresponds to `Context.filesDir`.
 * On Apple, this corresponds to `NSApplicationSupportDirectory`.
 * On JVM, this corresponds to a platform-specific application data directory.
 * On web targets this is the root of the browser's origin private file system.
 */
public expect suspend fun FileKit.filesDirectory(): PlatformFile

/**
 * Returns the directory for application cache files.
 *
 * On Android, this corresponds to `Context.cacheDir`.
 * On Apple, this corresponds to `NSCachesDirectory`.
 * On JVM, this corresponds to a platform-specific cache directory.
 * On web targets this is the `cache` directory in the browser's origin private
 * file system.
 */
public expect suspend fun FileKit.cacheDirectory(): PlatformFile

/**
 * Returns the directory for application databases.
 *
 * This is primarily relevant for Android where it corresponds to the databases directory.
 * On other platforms, it might fall back to `filesDir` or another appropriate location.
 */
public expect suspend fun FileKit.databasesDirectory(): PlatformFile
