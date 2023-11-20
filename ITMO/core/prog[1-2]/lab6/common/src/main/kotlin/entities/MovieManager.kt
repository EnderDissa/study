package common.entities

import common.SetOverflowException
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashSet


/**
 * Movie manager representative class
 */
class MovieManager {
    private val movieQueue: HashSet<Movie> = HashSet()
    private val creationDate: LocalDate = LocalDate.now()
    private val maxElements = 10000
    private var fileName: String? = null
    private val idSet = HashSet<Long>()

    /**
     * Get movie queue method
     *
     * @return queue of movies [PriorityQueue]
     * @author Markov Maxim 2023
     */
    fun getMovieQueue() = Collections.unmodifiableSet(movieQueue)

    /**
     * add movie to collection method
     *
     * @argument new element of collection [Movie]
     * @return true if element was added.
     * @author Markov Maxim 2023
     */
    fun addMovie(movie: Movie): Boolean {
        if (movieQueue.size >= maxElements) throw SetOverflowException("Collection has maximum elements")
        movieQueue.add(movie)
        return true
    }

    /**
     * remove element by id from collection
     *
     * @argument id element id from collection
     * @return true if element was deleted.
     * @author Markov Maxim 2023
     */
    fun removeElementById(id: Long): Boolean {
        var elementToDelete : Movie? = null
        for (element in movieQueue) {
            if (element.getId() == id) {
                elementToDelete = element
            }
        }


        if (elementToDelete != null) {
            movieQueue.remove(elementToDelete)
            idSet.add(elementToDelete.getId())
            return true
        }

        return false
    }

    fun giveId(): Long {
        if (idSet.size == 0) {
            return movieQueue.size.toLong() + 1
        }

        return idSet.iterator().next()
    }

    /**
     * get collection creation date
     *
     * @return collection creation date [LocalDate]
     * @author Markov Maxim 2023
     */
    fun getCreationDate() = creationDate

    /**
     * get collection class
     *
     * @return class of collection
     * @author Markov Maxim 2023
     */
    fun getCollectionClass() = movieQueue.javaClass

    /**
     * get number of collection elements
     *
     * @return number of elements in collection
     * @author Markov Maxim 2023
     */
    fun getCollectionNumberOfElements() = movieQueue.size

    fun setFileName(fileName: String) {
        this.fileName = fileName
    }
    fun save() {
        val file = File(fileName!!)
        val writer = FileWriter(file)


        for (movie in movieQueue) {
            val movieValues = arrayOf(movie.getName(), movie.getCoordinates().getX().toString(),
                movie.getCoordinates().getY().toString(), (movie.getOscarsCount() ?: "").toString(),
                movie.getLength().toString(), movie.getGenre().toString(),(movie.getMpaaRating() ?: "").toString(),
                movie.getScreenwriter().getName(), movie.getScreenwriter().getHeight().toString(),
                movie.getScreenwriter().getHairColor().toString(), movie.getScreenwriter().getNationality().toString(),
                movie.getId().toString(), movie.getCreationDate().toString() )
            writer.write(movieValues.joinToString(",")+"\n")
        }

        writer.close()
    }

    fun load() {
        val lines = ArrayList<String>()
        val scanner = Scanner(Paths.get(fileName!!))
        scanner.useDelimiter("\n")
        while (scanner.hasNext()) {
            lines.add(scanner.next())
        }
        scanner.close()
        for (line in lines) {
            val data = line.split(",")  // splitting by commas and writing to the collection

            var oscarsCount: Long? = null
            if (data[3].isNotEmpty()) oscarsCount = data[3].toLong()

            var mpaaRating: MpaaRating? = null
            if (data[6].isNotEmpty()) mpaaRating = MpaaRating.valueOf(data[6])

            var country: Country? = null
            if (data[10].isNotEmpty()) country = Country.valueOf(data[10])

            addMovie(
                Movie(
                    data[0], Coordinates(data[1].toFloat(), data[2].toDouble()), oscarsCount,
                    data[4].toInt(), MovieGenre.valueOf(data[5]), mpaaRating,
                    Person(
                        data[7],
                        data[8].toInt(),
                        Color.valueOf(data[9]),
                        country
                    ),
                    data[11].toLong(), LocalDate.parse(data[12], DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )
            )
        }
    }
}