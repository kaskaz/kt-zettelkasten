import base.Read
import base.Update
import base.Zettel
import base.zettel
import base.address

class UpdateCommand : Command<UpdateCommandInput, UpdateCommandResult> {
    override fun execute(input: UpdateCommandInput): UpdateCommandResult {
        val zettel = Read(FolderRepository)
            .execute(address { withIdentifier(input.identifier) })

        // update zettel

        Update(FolderRepository)
            .execute(zettel)
        }

        return UpdateCommandResult(zettel)
}

data class UpdateCommandInput(val identifier: String) : CommandInput

data class UpdateCommandResult(val zettel: Zettel) : CommandResult
