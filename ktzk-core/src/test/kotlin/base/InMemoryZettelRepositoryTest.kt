package base

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InMemoryZettelRepositoryTest {

    private val uut = InMemoryZettelRepository
    private val unexistingZettel = zettel { address { asInvalid() } }

    @Test
    fun `should perform all repository functions with success`() {
        val zettel = zettel { }

        uut.create(zettel)

        assertEquals(
            zettel,
            uut.read(zettel.address)
        )

        val updatedZettel = zettel.copy(content = Content(UUID.randomUUID().toString()))

        uut.update(updatedZettel)

        assertEquals(
            updatedZettel,
            uut.read(zettel.address)
        )

        uut.delete(zettel.address)
    }

    @Test
    fun `should throw exception when creating zettel that already exists`() {
        val zettel = zettel {}

        uut.create(zettel)

        assertFailsWith<ZettelRepositoryException> {
            uut.create(zettel)
        }
    }

    @Test
    fun `should throw exception when reading zettel that does not exists`() {
        assertFailsWith<ZettelRepositoryException> {
            uut.read(unexistingZettel.address)
        }
    }

    @Test
    fun `should throw exception when updating zettel that does not exists`() {
        assertFailsWith<ZettelRepositoryException> {
            uut.update(unexistingZettel)
        }
    }

    @Test
    fun `should throw exception when deleting zettel that does not exists`() {
        assertFailsWith<ZettelRepositoryException> {
            uut.delete(unexistingZettel.address)
        }
    }
}