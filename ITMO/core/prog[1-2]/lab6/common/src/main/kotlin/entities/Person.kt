package common.entities

import common.EmptyStringException
import common.ValueLessThanZeroException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

/**
 * Colors enum representative class
 */
enum class Color {
    GREEN,
    BLACK,
    WHITE,
    BROWN
}

/**
 * Countries enum representative class
 */
enum class Country {
    RUSSIA,
    SPAIN,
    CHINA,
    VATICAN,
    INDIA
}

/**
 * Person representation class
 */
@Serializable(Person.Companion::class)
class Person(private val name: String, private val height: Int,
             private val hairColor: Color, private val nationality: Country?) {
    init {
        checkNameRestrictions(name)
        checkHeightRestrictions(height)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = Person::class)
    companion object {
        @JvmStatic
        fun checkNameRestrictions(name: String) {
            if (name.isEmpty()) throw EmptyStringException("Person need's name")
            if (name.contains(",")) throw EmptyStringException("Name can't use symbol ','")
        }

        @JvmStatic
        fun checkHeightRestrictions(height: Int) {
            if (height <= 0) throw ValueLessThanZeroException("Height can't be less than zero")
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun serialize(encoder: Encoder, value: Person) {
            encoder.encodeStructure(descriptor) {
                encodeStringElement(descriptor, 0, value.name)
                encodeIntElement(descriptor, 1, value.height)
                encodeNullableSerializableElement(descriptor, 2, String.serializer(), value.nationality?.name)
                encodeStringElement(descriptor, 3, value.hairColor.name)
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): Person {
            return decoder.decodeStructure(descriptor) {
                var name: String = ""
                var height: Int = 0
                var nationality: Country? = null
                var hairColor: Color = Color.BLACK
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> name = decodeStringElement(descriptor, 0)
                        1 -> height = decodeIntElement(descriptor, 1)
                        2 -> {
                            val natString = decodeNullableSerializableElement(descriptor, 2, String.serializer())
                            nationality = if (natString != null)
                                Country.valueOf(natString)
                            else
                                null
                        }
                        3 -> hairColor = Color.valueOf(decodeStringElement(descriptor, 3))
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Person(name, height, hairColor, nationality)
            }
        }

        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Person") {
            element<String>("name")
            element<Int>("height")
            element<Country?>("nationality")
            element<Color>("hairColor")
        }
    }

    /**
     * Name getter method
     *
     * @return person name [String]
     * @author Markov Maxim 2023
     */
    fun getName() = this.name

    /**
     * Height getter method
     *
     * @return person height [Float]
     * @author Markov Maxim 2023
     */
    fun getHeight() = this.height

    /**
     * Hair color getter method
     *
     * @return person hair color [Color]
     * @author Markov Maxim 2023
     */
    fun getHairColor() = this.hairColor

    /**
     * Nationality getter method
     *
     * @return person nationality [Country]
     * @author Markov Maxim 2023
     */
    fun getNationality() = this.nationality

    override fun toString(): String = "Name: $name Height: $height Hair Color: $hairColor Nationality: $nationality"
}