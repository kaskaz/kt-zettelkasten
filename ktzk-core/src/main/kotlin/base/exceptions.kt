package base

open class ZettelRepositoryException(message: String) : Exception(message)

class ZettelNotFoundException(address: Address) : ZettelRepositoryException("Zettel with address ${address.identifier} not found.")

class ZettelAlreadyExistsException(address: Address) : ZettelRepositoryException("Zettel with address ${address.identifier} already exists.")
