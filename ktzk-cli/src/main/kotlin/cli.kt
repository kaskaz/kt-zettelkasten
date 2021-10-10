import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

import base.ZettelNotFoundException

class KtzkCli : CliktCommand(name = "ktzk") {
    override fun run() = Unit
}

/**
 * Creates a new zettel
 * 
 * Receives the title (mandatory) and list of tags (optional) as parameters.
 * TODO Opens the terminal editor to allow the user to introduce the hypertext and references.
 */
class Create : CliktCommand(help = "Creates a new zettel") {
    private val name by option(
        "-n", "--name",
        help = "Zettel's title"
    ).required()

    private val tags by option(
        "-t", "--tag",
        help = "Keyword that describes zettel"
    ).multiple()

    /**
     * 1. Creates a simplified template of the file with title and tags
     * 2. Opens the terminal editor to allow the user to edit title and tags, and add the hypertext
     * 3. Parses the title, tags and hypertext (with references mixed)
     * 4. Executes the command
     */
    override fun run() {
        CreateCommand()
            .execute(CreateCommandInput(name, tags.toSet(), "", emptyList()))
            .let { 
                echo("created a new zettel with id ${it.zettel.address.identifier}")
             }
    }
}

/**
 * Reads an existing zettel
 * 
 * Receives the address identifier (mandatory) and prints the zettel if exists
 * 
 * TODO Opens editor in read only mode or prints text like man (manual) command
 * TODO Remove try/catch with Promise (https://github.com/Shopify/promise-kotlin)
 */
class Read : CliktCommand(help = "Reads a given zettel") {
    private val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        try {
            ReadCommand()
                .execute(ReadCommandInput(identifier))
                .let { 
                    echo(
                        ZettelToStringConverter().convert(it.zettel)
                    )
                 }
        } catch(e: ZettelNotFoundException) {
            echo(e.message)
        }
    }
}
