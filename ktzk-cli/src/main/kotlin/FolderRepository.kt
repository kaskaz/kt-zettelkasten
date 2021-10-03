import base.*
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.TermUi.echo
import java.io.BufferedOutputStream
import java.io.File
import java.nio.file.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toFileHeaderFormat() = DateTimeFormatter.RFC_1123_DATE_TIME.format(this)

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
        val content =
            """
                ${zettel.address.identifier} - ${zettel.header.title}
                tags: ${zettel.header.tags.joinToString(separator = " ") { "#${it.value}" } }
                created at: ${zettel.header.createdAt.toFileHeaderFormat()} 
                updated at: ${zettel.header.updatedAt.toFileHeaderFormat()}
                
                ${zettel.content.hypertext}
            """.trimIndent()

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
        echo("read $address")
        return zettel {  }
    }

    override fun delete(address: Address) {
        echo("deleted $address")
    }
}