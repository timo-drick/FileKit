package io.github.vinceglb.filekit

/**
 * Builds the virtual directory tree exposed by web directory pickers.
 *
 * The browser only gives File entries through webkitdirectory, so empty
 * directories and real directory metadata are not available.
 */
public fun PlatformFile.Companion.fromWebDirectoryFiles(
    files: List<WebFile.FileWrapper>,
): PlatformFile? {
    if (files.isEmpty()) {
        return null
    }

    val rootName = files
        .firstNotNullOfOrNull { file ->
            file.path
                .split("/")
                .firstOrNull { it.isNotBlank() }
        }
        ?: return null

    val root = WebFile.DirectoryWrapper(
        name = rootName,
        path = rootName,
        parent = null,
    )
    val directories = mutableMapOf(root.path to root)

    files.forEach { file ->
        val segments = file.path
            .split("/")
            .filter { it.isNotBlank() }

        if (segments.size < 2 || segments.first() != rootName) {
            return@forEach
        }

        var parent = root
        var directoryPath = root.path

        segments
            .drop(1)
            .dropLast(1)
            .forEach { directoryName ->
                directoryPath = "$directoryPath/$directoryName"
                parent = directories.getOrPut(directoryPath) {
                    val directory = WebFile.DirectoryWrapper(
                        name = directoryName,
                        path = directoryPath,
                        parent = parent,
                    )
                    parent.mutableChildren.add(directory)
                    directory
                }
            }

        parent.mutableChildren.add(
            WebFile.FileWrapper(
                file = file.file,
                path = file.path,
                parent = parent,
            ),
        )
    }

    return PlatformFile(root)
}
