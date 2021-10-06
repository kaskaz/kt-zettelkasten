import kotlin.collections.List;

class CreateCommand : Command<CreateCommandInput, CreateCommandResult> {
    override fun execute(input: CreateCommandInput): CreateCommandResult {
        return CreateCommandResult()
    }
}

data class CreateCommandInput(
    val title: String,
    val tags: Set<String>,
    val hypertext: String,
    val references: List<String>
) : CommandInput

class CreateCommandResult : CommandResult {}

