import base.*
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.TermUi.echo
import java.io.BufferedOutputStream
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.List
import kotlin.io.path.name
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.concurrent.thread

fun OffsetDateTime.toFileHeaderFormat() = DateTimeFormatter.RFC_1123_DATE_TIME.format(this)
fun String.fromFileHeaderFormat(): OffsetDateTime = OffsetDateTime.parse(this, DateTimeFormatter.RFC_1123_DATE_TIME)

object FolderRepository : ZettelRepository {

    val SYSTEM_CONFIG_FILEPATH: String = System.getProperty("user.dir")
    val FINAL_DIRECTORY_PATH: String = "/zettels"

    /**
     * The directory to store zettels
     *
     * Retrieves folder path from user configuration and creates it if does not exists
     */
    private val directory: File by lazy {
        val finalPath = Path.of(SYSTEM_CONFIG_FILEPATH + FINAL_DIRECTORY_PATH)
        val directory = File(finalPath.toUri())

        if (!directory.exists()) {
            Files.createDirectory(finalPath)
            echo("Directory created: $finalPath")
        }

        directory
    }

    override fun create(zettel: Zettel) {
        /**
         * Filename
         */
        val filename = zettel.address.identifier + "--" + zettel.header.title.replace(" ", "-")
        val filepath = "${this.directory.path}/$filename"

        /**
         * Format zettel file
         */
        val content = ZettelToStringConverter()
            .convert(zettel)

        val fullContent = TermUi.editText(content, requireSave = true) ?: content

        /**
         * Save file
         */
        try {
            val bos = BufferedOutputStream(
                Files.newOutputStream(
                    File(filepath).toPath(),
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE
                )
            )
            bos.write(fullContent.toByteArray())
            bos.close()
        } catch (e: Exception) {
            throw ZettelRepositoryException(e.localizedMessage)
        }

        /**
         * Update index
         */

    }

    override fun update(zettel: Zettel) {
        echo("updated $zettel")
    }

    override fun read(address: Address): Zettel {
        val finder = FindZettelFile(address)
        Files.walkFileTree(this.directory.toPath(), finder)

        if (!finder.isFileFound())
            throw ZettelNotFoundException(address)

        return FileToZettelConverter()
            .convert(finder.getFile())
    }

    override fun delete(address: Address) {
        val finder = FindZettelFile(address)
        Files.walkFileTree(this.directory.toPath(), finder)

        if (!finder.isFileFound())
            throw ZettelNotFoundException(address)

        Files.delete(finder.getFile().toPath())
    }

    private class FindZettelFile(val address: Address) : SimpleFileVisitor<Path>() {

        private val regex = Regex("${address.identifier}--\\D*")
        private val filesFound = mutableListOf<Path>()

        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            file?.name?.let {
                if (regex.matches(it)) {
                    filesFound.add(file)
                }
            }
            return super.visitFile(file, attrs)
        }

        fun isFileFound(): Boolean = filesFound.isNotEmpty()

        fun getFile(): File = File(filesFound.first().toUri())
    }

    private class ZettelToStringConverter {
        fun convert(zettel: Zettel): String =
            """
                ${zettel.address.identifier} - ${zettel.header.title}
                tags: ${zettel.header.tags.joinToString(separator = " ") { "#${it.value}" } }
                created at: ${zettel.header.createdAt.toFileHeaderFormat()} 
                updated at: ${zettel.header.updatedAt.toFileHeaderFormat()}
                
                ${zettel.content.hypertext}
            """.trimIndent()
    }

    private class FileToZettelConverter {
        fun convert(file: File): Zettel {
            if(!file.exists() || !file.isFile)
                throw ZettelRepositoryException("'${file.name}' is does not exists or is not a file.")

            val lines = file.readLines()
            
            return zettel {
                withAddress(
                    address { 
                        withIdentifier(readIdentifier(lines))
                    }
                )
                withHeader(
                    header {
                        withTitle(readTitle(lines))
                        withTags(readTags(lines))
                        withCreatedAt(readCreatedAt(lines))
                        withUpdatedAt(readUpdatedAt(lines))
                    }
                )
                withContent(
                    content {
                        withHypertext(readHypertext(lines))
                    }
                )
            }
        }

        private fun readIdentifier(lines: List<String>): String {
            return lines[0].split('-').first().trim()
        }

        private fun readTitle(lines: List<String>): String {
            return lines[0].split('-', limit = 2).last().trim()
        }

        private fun readTags(lines: List<String>): Set<Tag> {
            return lines[1]
                .replace("tags:", "").trim()
                .split(' ').map { Tag(it.drop(1)) }
                .toSet()
        }

        private fun readCreatedAt(lines: List<String>): OffsetDateTime {
            return lines[2]
                .replace("created at:", "").trim()
                .let { it.fromFileHeaderFormat() }
        }

        private fun readUpdatedAt(lines: List<String>): OffsetDateTime {
            return lines[3]
                .replace("updated at:", "").trim()
                .let { it.fromFileHeaderFormat() }
        }

        private fun readHypertext(lines: List<String>): String {
            return lines
                .subList(4, lines.size)
                .joinToString(separator = "\n", postfix = "\n")
        }
    }
}
