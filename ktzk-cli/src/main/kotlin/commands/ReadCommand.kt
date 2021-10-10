import base.Read
import base.Zettel
import base.zettel
import base.address

class ReadCommand : Command<ReadCommandInput, ReadCommandResult> {
    override fun execute(input: ReadCommandInput): ReadCommandResult {
        return Read(FolderRepository)
            .execute(address{ withIdentifier(input.identifier) })
            .let { ReadCommandResult(it) }
    }
}

data class ReadCommandInput(val identifier: String) : CommandInput

data class ReadCommandResult(val zettel: Zettel) : CommandResult
