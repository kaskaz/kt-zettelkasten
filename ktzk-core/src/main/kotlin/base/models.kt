package base

import java.time.OffsetDateTime

data class Zettel(
    val address: Address,
    val header: Header,
    val content: Content,
    val footer: Footer
)

/**
 * Time based unique identifier for Zettel
 */
data class Address(val identifier: String)

/**
 * Set of data that identifies the Zettel
 */
data class Header(
    val title: String,
    val tags: Set<Tag>,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

/**
 * Main topics discussed on Zettel
 */
data class Tag(val value: String)

/**
 * Body of Zettel
 */
data class Content(val hypertext: String)

/**
 * A list of references for the Zettel
 */
data class Footer(val references: List<Reference>)

/**
 * External reference to the Zettel in context
 */
sealed interface Reference

/**
 * Book, article, etc...
 */
data class Citation(val text: String) : Reference

/**
 * An existant Zettel
 * Eg: [[20210918001200]] if address is time-based
 */
data class Link(val url: String) : Reference