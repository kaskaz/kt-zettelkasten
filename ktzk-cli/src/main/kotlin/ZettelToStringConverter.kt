import base.Zettel

class ZettelToStringConverter {
    fun convert(zettel: Zettel): String =
        """
            ${zettel.address.identifier} - ${zettel.header.title}
            tags: ${zettel.header.tags.joinToString(separator = " ") { "#${it.value}" } }
            created at: ${zettel.header.createdAt.toFileHeaderFormat()} 
            updated at: ${zettel.header.updatedAt.toFileHeaderFormat()}
            
            ${zettel.content.hypertext}
        """.trimIndent()
}
