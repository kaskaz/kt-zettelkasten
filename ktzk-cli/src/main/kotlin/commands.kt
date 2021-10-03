import base.Create
import base.Tag
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

class KtzkCli : CliktCommand(name = "ktzk") {
    override fun run() = Unit
}

class Create : CliktCommand(help = "Creates a new zettel") {
    val name by option(
        "-n", "--name",
        help = "Zettel's title"
    ).required()

    val tags by option(
        "-t", "--tag",
        help = "Keyword that describes zettel"
    ).multiple()

    override fun run() {
        val operation = Create(FolderRepository)
        val zettel = operation.execute(
            title = name,
            tags = tags.map { Tag(it) }.toSet(),
            hypertext = "dummy text",
            references = emptyList()
        )
        echo()
    }
}

class Read : CliktCommand(help = "Reads a given zettel") {
    val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        echo("zettel read $identifier")
    }
}

class Update : CliktCommand(help = "Updates a given zettel") {
    val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        echo("zettel updated $identifier")
    }
}

class Delete : CliktCommand(help = "Deletes a given zettel") {
    val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        echo("zettel updated $identifier")
    }
}

class List : CliktCommand(help = "List zettels") {
    override fun run() {
        echo("list of zettels")
    }
}
