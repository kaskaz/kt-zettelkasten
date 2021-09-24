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

    fun build(): Zettel {
        val now = OffsetDateTime.now()
        return Zettel(
            address ?: AddressBuilder().withValidAddress().build(),
            header ?: Header("", emptySet(), now, now),
            content ?: Content(""),
            footer ?: Footer(emptyList())
        )
    }
}

class AddressBuilder {

    var identifier: String? = null

    /**
     * Can be used to generate a new and valid address
     */
    fun withValidAddress() = apply { identifier = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).toZettelAddress() }

    /**
     * Assumes an invalid pattern
     */
    fun withInvalidAddress() = apply { identifier = UUID.randomUUID().toString() }

    fun build(): Address {
        if (identifier == null) withValidAddress()
        return Address(identifier!!)
    }
}

class HeaderBuilder {

    var title: String? = null
    var tags: Set<Tag>? = null
    var createdAt: OffsetDateTime? = null
    var updatedAt: OffsetDateTime? = null

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
    fun build(): Content = Content(hypertext ?: "")
}

class FooterBuilder {
    var references: List<Reference>? = null
    fun build(): Footer = Footer(references ?: emptyList())
}
