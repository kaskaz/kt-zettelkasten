package base

fun zettel(init: ZettelBuilder.() -> Unit): Zettel = ZettelBuilder().apply(init).build()

fun address(init: AddressBuilder.() -> Unit): Address = AddressBuilder().apply(init).build()

fun AddressBuilder.asInvalid() = this.withInvalidAddress()

fun header(init: HeaderBuilder.() -> Unit): Header = HeaderBuilder().apply(init).build()

fun content(init: ContentBuilder.() -> Unit): Content = ContentBuilder().apply(init).build()

fun footer(init: FooterBuilder.() -> Unit): Footer = FooterBuilder().apply(init).build()
