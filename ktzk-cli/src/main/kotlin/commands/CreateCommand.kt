import base.Create
import base.Link
import base.Tag
import base.Zettel
import kotlin.collections.List

class CreateCommand : Command<CreateCommandInput, CreateCommandResult> {
    override fun execute(input: CreateCommandInput): CreateCommandResult {
        return Create(FolderRepository)
            .execute(
                title = input.title,
                tags = input.tags.map { Tag(it) }.toSet(),
                hypertext = input.hypertext,
                references = input.references.map { Link(it) }.toList()
            )
            .let { CreateCommandResult(it) }
    }
}

data class CreateCommandInput(
    val title: String,
    val tags: Set<String>,
    val hypertext: String,
    val references: List<String>
) : CommandInput

data class CreateCommandResult(val zettel: Zettel) : CommandResult
