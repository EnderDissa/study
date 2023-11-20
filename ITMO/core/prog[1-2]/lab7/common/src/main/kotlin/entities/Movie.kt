package common.entities

import common.EmptyStringException
import common.ValueLessThanZeroException
import kotlinx.serialization.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * Movie genres enum representative class
 */
enum class MovieGenre {
    ACTION,
    COMEDY,
    TRAGEDY,
    FANTASY
}

/**
 * Mpaa ratings enum representative class
 */
enum class MpaaRating {
    G,
    PG,
    PG_13,
    R,
    NC_17
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val result = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        encoder.encodeString(result)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

@Serializable(Movie.Companion::class)
class Movie {
    private var name: String
    private var coordinates: Coordinates
    private var oscarsCount: Long?
    private var length: Int
    private var genre: MovieGenre
    private var mpaaRating: MpaaRating?
    private var screenWriter: Person
    private var id: Long
    private var creationDate: LocalDate
    private var owner: String = ""

    constructor(name: String, coordinates: Coordinates,
                oscarsCount: Long?, length: Int,
                genre: MovieGenre, mpaaRating: MpaaRating?,
                screenWriter: Person, id: Long, date: LocalDate) {
        checkNameRestrictions(name)
        checkOscarsCountRestrictions(oscarsCount)
        checkLengthRestrictions(length)

        this.name = name
        this.coordinates = coordinates
        this.oscarsCount = oscarsCount
        this.length = length
        this.genre = genre
        this.mpaaRating = mpaaRating
        this.screenWriter = screenWriter

        this.id = id
        cntId = max(id, cntId)
        this.creationDate = date
    }
    constructor(name: String, coordinates: Coordinates,
                oscarsCount: Long?, length: Int,
                genre: MovieGenre, mpaaRating: MpaaRating?,
                screenWriter: Person, id: Long) {
        checkNameRestrictions(name)
        checkOscarsCountRestrictions(oscarsCount)
        checkLengthRestrictions(length)

        this.name = name
        this.coordinates = coordinates
        this.oscarsCount = oscarsCount
        this.length = length
        this.genre = genre
        this.mpaaRating = mpaaRating
        this.screenWriter = screenWriter

        this.id = id

        cntId = max(id, cntId)
        this.creationDate = LocalDate.now()
    }

    constructor(name: String, coordinates: Coordinates,
                oscarsCount: Long?, length: Int,
                genre: MovieGenre, mpaaRating: MpaaRating?,
                screenWriter: Person, id: Long, date: LocalDate, owner: String) {
        checkNameRestrictions(name)
        checkOscarsCountRestrictions(oscarsCount)
        checkLengthRestrictions(length)

        this.name = name
        this.coordinates = coordinates
        this.oscarsCount = oscarsCount
        this.length = length
        this.genre = genre
        this.mpaaRating = mpaaRating
        this.screenWriter = screenWriter

        this.id = id
        this.owner = owner

        cntId = max(id, cntId)
        this.creationDate = date
    }

    constructor(name: String, coordinates: Coordinates,
                oscarsCount: Long?, length: Int,
                genre: MovieGenre, mpaaRating: MpaaRating?,
                screenWriter: Person) {
        checkNameRestrictions(name)
        checkOscarsCountRestrictions(oscarsCount)
        checkLengthRestrictions(length)

        this.name = name
        this.coordinates = coordinates
        this.oscarsCount = oscarsCount
        this.length = length
        this.genre = genre
        this.mpaaRating = mpaaRating
        this.screenWriter = screenWriter
        id = giveId()
        creationDate = LocalDate.now()

    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = Movie::class)
    companion object {
        fun checkNameRestrictions(name: String) {
            if (name.isEmpty()) throw EmptyStringException("Movie name can't be empty")
            if (name.contains(",")) throw EmptyStringException("Movie name can't use symbol ','")
        }

        fun checkOscarsCountRestrictions(oscarsCount: Long?) {
            if (oscarsCount != null)
                if (oscarsCount <= 0)
                    throw ValueLessThanZeroException("Oscars count needs to be more than zero")
        }

        fun checkLengthRestrictions(length: Int) {
            if (length <= 0) throw ValueLessThanZeroException("Length needs to be more than zero")
        }

        private var cntId: Long = 0

        /**
         * Giving id to movie instance method
         *
         * @return id of new movie [Long]
         */
        @JvmStatic
        private fun giveId(): Long {
            cntId += 1
            return cntId
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun serialize(encoder: Encoder, value: Movie) {
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, value.name)
                encodeSerializableElement(descriptor, 1, Coordinates.serializer(), value.coordinates)
                encodeNullableSerializableElement(descriptor, 2, Long.serializer(), value.oscarsCount)
                encodeIntElement(descriptor, 3, value.length)
                encodeStringElement(descriptor, 4, value.genre.name)
                encodeNullableSerializableElement(descriptor, 5, String.serializer(), value.mpaaRating?.name)
                encodeSerializableElement(descriptor, 6, Person.serializer(), value.screenWriter)
                encodeLongElement(descriptor, 7, value.id)
                encodeSerializableElement(descriptor, 8, LocalDateSerializer, value.creationDate)
                encodeStringElement(descriptor, 9, value.owner)
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): Movie {
            return decoder.decodeStructure(descriptor) {
                var name = ""
                var coordinates = Coordinates(1f, 1.0)
                var oscarsCount: Long? = null
                var length = 12
                var genre: MovieGenre = MovieGenre.ACTION
                var mpaaRating: MpaaRating? = null
                var screenWriter = Person("hj", 1, Color.BLACK, null)
                var id: Long = 0
                var creationDate: LocalDate = LocalDate.now()
                var owner = ""
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> name = decodeStringElement(descriptor, 0)
                        1 -> coordinates = decodeSerializableElement(descriptor, 1, Coordinates.serializer())
                        2 -> oscarsCount = decodeNullableSerializableElement(descriptor, 2, Long.serializer())
                        3 -> length = decodeIntElement(descriptor, 3)
                        4 -> genre = MovieGenre.valueOf(decodeStringElement(descriptor, 4))
                        5 -> {
                            val str = decodeNullableSerializableElement(descriptor, 5, String.serializer())
                            mpaaRating = if (str != null)
                                MpaaRating.valueOf(str)
                            else null
                        }
                        6 -> screenWriter = decodeSerializableElement(descriptor, 6, Person.serializer())
                        7 -> id = decodeLongElement(descriptor, 7)
                        8 -> creationDate = decodeSerializableElement(descriptor, 8, LocalDateSerializer)
                        9 -> owner = decodeStringElement(descriptor, 9)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Movie(name, coordinates, oscarsCount, length, genre, mpaaRating, screenWriter, id, creationDate, owner)
            }
        }

        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie") {
            element<String>("name")
            element<Coordinates>("coordinates")
            element<Long?>("oscarsCount")
            element<Int>("length")
            element<MovieGenre>("genre")
            element<MpaaRating?>("mpaaRating")
            element<Person>("screenwriter")
            element<Long>("id")
            element<String>("creationDate")
            element<String>("owner")
        }
    }

    fun setNewId(id: Long) {
        this.id = id
    }

    /**
     * Name getter method
     *
     * @return movie name [String]
     * @author Markov Maxim 2023
     */
    fun getName() = this.name

    /**
     * Coordinates getter method
     *
     * @return movie coordinates [Coordinates]
     * @author Markov Maxim 2023
     */
    fun getCoordinates() = this.coordinates

    /**
     * Oscars count getter method
     *
     * @return movie oscars count [Int]
     * @author Markov Maxim 2023
     */
    fun getOscarsCount() = this.oscarsCount

    /**
     * Length getter method
     *
     * @return movie length [Long]
     * @author Markov Maxim 2023
     */
    fun getLength() = this.length

    /**
     * Genre getter method
     *
     * @return movie genre [MovieGenre]
     * @author Markov Maxim 2023
     */
    fun getGenre() = this.genre

    /**
     * Mpaa rating getter method
     *
     * @return movie mpaa rating [mpaaRating]
     * @author Markov Maxim 2023
     */
    fun getMpaaRating() = this.mpaaRating
    /**
     * Sreenwriter's name getter method
     *
     * @return movie id [Long]
     * @author Markov Maxim 2023
     */
    fun getScreenwriter() = this.screenWriter

    /**
     * Id getter method
     *
     * @return movie id [Long]
     * @author Markov Maxim 2023
     */
    fun getId() = this.id

    /**
     * Creation date getter method
     *
     * @return movie creation date [LocalDate]
     * @author Markov Maxim 2023
     */
    fun getCreationDate() = this.creationDate


    override fun toString(): String {
        return "ID: $id\nName: $name\nCoordinates: $coordinates\nCreation date: $creationDate\n" +
                "Oscars count: $oscarsCount\nLenght: $length\n Genre: $genre\n Mpaa rating: $mpaaRating\n" +
                "Screen writer: $screenWriter"
    }
}