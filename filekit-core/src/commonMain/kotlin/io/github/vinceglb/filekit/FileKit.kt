package io.github.vinceglb.filekit

public expect object FileKit

/**
 * Returns the directory for persistent application files.
 *
 * On web targets this is the root of the browser's origin private file system.
 */
public expect suspend fun FileKit.filesDirectory(): PlatformFile

/**
 * Returns the directory for application cache files.
 *
 * On web targets this is the `cache` directory in the browser's origin private
 * file system.
 */
public expect suspend fun FileKit.cacheDirectory(): PlatformFile

/**
 * Returns the directory for application databases.
 *
 * On web targets this is the `databases` directory in the browser's origin
 * private file system.
 */
public expect suspend fun FileKit.databasesDirectory(): PlatformFile
