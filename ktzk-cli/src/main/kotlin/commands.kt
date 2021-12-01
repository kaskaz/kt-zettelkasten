import base.Create
import base.Delete
import base.Read
import base.Tag
import base.Update
import base.address
import base.ZettelNotFoundException
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

class Update : CliktCommand(help = "Updates a given zettel") {
    private val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        identifier.let {
           
        }

    }
}

class Delete : CliktCommand(help = "Deletes a given zettel") {
    private val identifier by option(
        "-i", "--id",
        help = "Zettel's identifier"
    ).required()

    override fun run() {
        try {
            identifier.let {
                Delete(FolderRepository)
                    .delete(address { withIdentifier(it) })
            }
        } catch(e: ZettelNotFoundException) {
            echo(e.message)
        }
    }
}

class List : CliktCommand(help = "List zettels") {
    override fun run() {
        echo("list of zettels")
    }
}
