import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun OffsetDateTime.toZettelAddress() = this.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
