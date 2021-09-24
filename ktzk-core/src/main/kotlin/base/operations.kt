package base

import java.time.OffsetDateTime

/**
 * Creates a Zettel by specifying its Content, Footer and Header
 * The Address is created when calling this method to grant a unique unit
 */
class Create(private val zettelRepository: ZettelRepository) {
    fun execute(
        title: String,
        tags: Set<Tag>,
        hypertext: String,
        references: List<Reference>
    ): Zettel {
        val zettel = zettel {
            address { withValidAddress() }
            header {
                title
                tags
            }
            content {
                hypertext
            }
            footer {
                references
            }
        }
        zettelRepository.create(zettel)
        return zettel
    }
}

class Read(private val zettelRepository: ZettelRepository) {
    fun execute(address: Address): Zettel {
        return zettelRepository.read(address)
    }
}

class Update(private val zettelRepository: ZettelRepository) {
    fun execute(zettel: Zettel) {
        zettelRepository.update(
            zettel.copy(
                header = zettel.header.copy(
                    updatedAt = OffsetDateTime.now()
                )
            )
        )
    }
}

class Delete(private val zettelRepository: ZettelRepository) {
    fun delete(zettel: Zettel) {
        zettelRepository.delete(zettel.address)
    }
}

/**
 * Can have different implementations for diferent criterias (date, text, address, tag)
 * Can have meta information about the result
 * Can have multiple entries and be search-limited
 */
class Search {
    fun execute(criteria: SearchCriteria): SearchResult {
        return SearchResult()
    }
}

class SearchCriteria

class SearchResult