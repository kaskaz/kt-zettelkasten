interface Command <I: CommandInput, O: CommandResult> {
    fun execute(input: I): O
}

interface CommandInput

interface CommandResult