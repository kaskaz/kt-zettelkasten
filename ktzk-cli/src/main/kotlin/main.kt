import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) =
    KtzkCli()
        .subcommands(
            Create(),
            Read(),
            Update(),
            Delete(),
            List()
        )
        .main(args)
