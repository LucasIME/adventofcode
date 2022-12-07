package q7

data class FileSystemNode(
    val name: String,
    val parent: FileSystemNode?,
    val childrenByName: MutableMap<String, FileSystemNode>,
    val isFile: Boolean,
    val size: Int? = null
)
