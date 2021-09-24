package base

fun zettel(init: ZettelBuilder.() -> Unit): Zettel {
    val builder = ZettelBuilder()
    builder.init()
    return builder.build()
}

fun address(init: AddressBuilder.() -> Unit): Address {
    val builder = AddressBuilder()
    builder.apply(init)
    //builder.init()
    return builder.build()
}

fun AddressBuilder.asInvalid() = this.withInvalidAddress()

fun header(init: HeaderBuilder.() -> Unit): Header {
    val builder = HeaderBuilder()
    builder.init()
    return builder.build()
}

fun content(init: ContentBuilder.() -> Unit): Content {
    val builder = ContentBuilder()
    builder.init()
    return builder.build()
}

fun footer(init: FooterBuilder.() -> Unit): Footer {
    val builder = FooterBuilder()
    builder.init()
    return builder.build()
}
