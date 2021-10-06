package base

interface ZettelRepository {
    fun create(zettel: Zettel)
    fun update(zettel: Zettel)
    fun read(address: Address): Zettel
    fun delete(address: Address)
}

object InMemoryZettelRepository : ZettelRepository {

    private val mapOfZettels = mutableMapOf<String, Zettel>()

    override fun create(zettel: Zettel) {
        if(exists(zettel.address))
            throw ZettelAlreadyExistsException(zettel.address)
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
            throw ZettelNotFoundException(address)
        }
    }

    private fun exists(address: Address): Boolean = mapOfZettels.contains(address.identifier)

    private fun addOrReplace(zettel: Zettel) = mapOfZettels.put(zettel.address.identifier, zettel)
}
