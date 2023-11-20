package common.entities

import common.MaxValueException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*


@Serializable(Coordinates.Companion::class)
class Coordinates(private val x: Float, private val y: Double) {
    init {
        checkYRestrictions(y)
    }

    /**
     * X getter method
     *
     * @return x coordinate [Float]
     * @author Markov Maxim 2023
     */
    fun getX() = this.x

    /**
     * Y getter method
     *
     * @return y coordinate [Double]
     * @author Markov Maxim 2023
     */
    fun getY() = this.y

    override fun toString(): String {
        return "X: $x Y: $y"
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = Coordinates::class)
    companion object : KSerializer<Coordinates> {

        override fun serialize(encoder: Encoder, value: Coordinates) {
            encoder.encodeStructure(descriptor) {
                encodeFloatElement(descriptor, 0, value.x)
                encodeDoubleElement(descriptor, 1, value.y)
            }
        }

        override fun deserialize(decoder: Decoder): Coordinates {
            return decoder.decodeStructure(descriptor) {
                var x = 0f
                var y = 0.0
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> x = decodeFloatElement(descriptor, 0)
                        1 -> y = decodeDoubleElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Coordinates(x, y)
            }
        }

        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Coordinates") {
            element<Float>("x")
            element<Double>("y")
        }

        fun checkYRestrictions(y: Double) {
            if (y > 424) throw MaxValueException("Y can't be more than 424")
        }
    }
}