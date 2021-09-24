package base

interface ZettelRepository {
    fun create(zettel: Zettel)
    fun update(zettel: Zettel)
    fun read(address: Address): Zettel
    fun delete(address: Address)
}

class ZettelRepositoryException(message: String) : Exception(message)

object InMemoryZettelRepository : ZettelRepository {

    private val mapOfZettels = mutableMapOf<String, Zettel>()

    override fun create(zettel: Zettel) {
        if(exists(zettel.address))
            throw ZettelRepositoryException("Zettel with address ${zettel.address.identifier} already exists.")
        addOrReplace(zettel)
    }

    override fun update(zettel: Zettel) {
        notExistsOrThrow(zettel.address)
        addOrReplace(zettel)
    }

    override fun read(address: Address): Zettel {
        notExistsOrThrow(address)
        return mapOfZettels[address.identifier]!!
    }

    override fun delete(address: Address) {
        notExistsOrThrow(address)
        mapOfZettels.remove(address.identifier)
    }

    private fun notExistsOrThrow(address: Address) {
        if(!exists(address)) {
            throw ZettelRepositoryException("Zettel with address ${address.identifier} does not exists.")
        }
    }

    private fun exists(address: Address): Boolean = mapOfZettels.contains(address.identifier)

    private fun addOrReplace(zettel: Zettel) = mapOfZettels.put(zettel.address.identifier, zettel)
}
