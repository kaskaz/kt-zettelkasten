package base

import toZettelAddress
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class ZettelBuilder {

    var address: Address? = null
    var header: Header? = null
    var content: Content? = null
    var footer: Footer? = null

    fun withAddress(address: Address) = apply{ this.address = address }
    fun withHeader(header: Header) = apply { this.header = header }
    fun withContent(content: Content) = apply { this.content = content }
    fun withFooter(footer: Footer) = apply { this.footer = footer }

    fun build(): Zettel {
        val now = OffsetDateTime.now()
        return Zettel(
            address ?: AddressBuilder().withValidIdentifier().build(),
            header ?: Header("", emptySet(), now, now),
            content ?: Content(""),
            footer ?: Footer(emptyList())
        )
    }
}

class AddressBuilder {

    var identifier: String? = null

    fun withIdentifier(identifier: String) = apply { this.identifier = identifier }

    /**
     * Can be used to generate a new and valid address
     */
    fun withValidIdentifier() = apply { identifier = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).toZettelAddress() }

    /**
     * Assumes an invalid pattern
     */
    fun withInvalidIdentifier() = apply { identifier = UUID.randomUUID().toString() }

    fun build(): Address {
        if (identifier == null) withValidIdentifier()
        return Address(identifier!!)
    }
}

class HeaderBuilder {

    var title: String? = null
    var tags: Set<Tag>? = null
    var createdAt: OffsetDateTime? = null
    var updatedAt: OffsetDateTime? = null

    fun withTitle(title: String) = apply { this.title = title }
    fun withTags(tags: Set<Tag>) = apply { this.tags = tags }
    fun withCreatedAt(createdAt: OffsetDateTime) = apply { this.createdAt = createdAt }
    fun withUpdatedAt(updatedAt: OffsetDateTime) = apply { this.updatedAt = updatedAt }

    fun build(): Header {
        val now = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC)
        return Header(
            title = title ?: "",
            tags = tags ?: emptySet(),
            createdAt = createdAt ?: now,
            updatedAt = updatedAt ?: now
        )
    }

}

class ContentBuilder {
    var hypertext: String?  = null

    fun withHypertext(hypertext: String) = apply { this.hypertext = hypertext }

    fun build(): Content = Content(hypertext ?: "")
}

class FooterBuilder {
    var references: List<Reference>? = null

    fun withReferences(references: List<Reference>) = apply { this.references = references }

    fun build(): Footer = Footer(references ?: emptyList())
}
