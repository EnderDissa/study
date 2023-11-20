package managers

import ConcurrentHashSet
import common.entities.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.security.MessageDigest
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types
import java.util.*


class DataBaseManager {
    private val user: String
    private val password: String
    private val url: String
    private val connection: Connection
    private val PEPPER = "trJS%7]sa"
    private val hashingAlgorithm: MessageDigest
    private val logger: Logger = LoggerFactory.getLogger(DataBaseManager::class.java)

    init {
        val properties = Properties()

        FileReader("db.cfg").use { reader ->
            properties.load(reader)
        }

        user = properties.getProperty("user")
        password = properties.getProperty("password")
        url = properties.getProperty("URL")

        connection = DriverManager.getConnection(url, user, password)
        logger.info("Connected to database")
        hashingAlgorithm = MessageDigest.getInstance("SHA-224")
        logger.info("Hashing algorithm SHA-224")
        dataBaseCreate()
    }

    companion object {
        private val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        fun generateRandomString(length: Int): String {
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
    private fun getSHA224Hash(input: String): String {
        val inputBytes = input.toByteArray()
        hashingAlgorithm.update(inputBytes)
        val hashBytes = hashingAlgorithm.digest()
        val sb = StringBuilder()

        for (b in hashBytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
    private fun dataBaseCreate() = try {
            connection.prepareStatement(DataBaseRequests.CREATE)
                .execute()
        } catch (e: Exception) {
            logger.error("Database was already created")
        }

    fun addUser(user: User) {
        val username = user.getUsername()
        val salt = generateRandomString(10)
        val password = PEPPER + user.getPassword() + salt
        val ps = connection.prepareStatement(DataBaseRequests.ADD_USER)
        if (checkUser(username)) throw SQLException()
        ps.setString(1, username)
        ps.setString(2, getSHA224Hash(password))
        ps.setString(3, salt)
        ps.execute()
        logger.info("Added user $user")
    }

    fun confirmUser(user: User): Boolean {
        return try {
            val username: String = user.getUsername()
            val getUser = connection.prepareStatement(DataBaseRequests.GET_USER)
            getUser.setString(1, username)
            val resultSet = getUser.executeQuery()
            if (resultSet.next()) {
                val salt = resultSet.getString("salt")
                val toCheckPass: String = getSHA224Hash(PEPPER + user.getPassword() + salt)
                toCheckPass == resultSet.getString("password")
            } else {
                false
            }
        } catch (e: SQLException) {
            logger.error("Unresolved SQL command $e!")
            false
        }
    }

    private fun checkUser(username: String): Boolean {
        val ps = connection.prepareStatement(DataBaseRequests.GET_USER)
        ps.setString(1, username)
        val resultSet = ps.executeQuery()
        return resultSet.next()
    }

    fun addMovie(movie: Movie, user: User) = try {
        val ps = connection.prepareStatement(DataBaseRequests.ADD_MOVIE)

        ps.setString(1, movie.getName())
        ps.setFloat(2, movie.getCoordinates().getX())
        ps.setDouble(3, movie.getCoordinates().getY())
        ps.setObject(4, movie.getOscarsCount(), Types.BIGINT)
        ps.setInt(5, movie.getLength())
        ps.setObject(6, movie.getGenre(), Types.OTHER)
        ps.setObject(7, movie.getMpaaRating(), Types.OTHER)
        ps.setString(8, movie.getScreenwriter().getName())
        ps.setInt(9, movie.getScreenwriter().getHeight())
        ps.setObject(10, movie.getScreenwriter().getHairColor(), Types.OTHER)
        ps.setObject(11, movie.getScreenwriter().getNationality(), Types.OTHER)
        ps.setDate(12, java.sql.Date.valueOf(movie.getCreationDate()))
        ps.setString(13, user.getUsername())

        val resultSet = ps.executeQuery()

        if (!resultSet.next()) {
            logger.error("Movie hasn't been added")
            -1
        } else {
            logger.info("Movie has been added")
            resultSet.getInt(1)
        }
    } catch (e: SQLException) {
        logger.info("Movie hasn't been added")
        -1
    }

    fun updateMovie(id: Int, movie: Movie, user: User) = try {
        val ps = connection.prepareStatement(DataBaseRequests.UPDATE_MOVIE)

        ps.setString(1, movie.getName())
        ps.setFloat(2, movie.getCoordinates().getX())
        ps.setDouble(3, movie.getCoordinates().getY())
        ps.setObject(4, movie.getOscarsCount(), Types.BIGINT)
        ps.setInt(5, movie.getLength())
        ps.setObject(6, movie.getGenre(), Types.OTHER)
        ps.setObject(7, movie.getMpaaRating(), Types.OTHER)
        ps.setString(8, movie.getScreenwriter().getName())
        ps.setInt(9, movie.getScreenwriter().getHeight())
        ps.setObject(10, movie.getScreenwriter().getHairColor(), Types.OTHER)
        ps.setObject(11, movie.getScreenwriter().getNationality(), Types.OTHER)
        ps.setDate(12, java.sql.Date.valueOf(movie.getCreationDate()))
        ps.setInt(13, id)
        ps.setString(14, user.getUsername())

        val resultSet = ps.executeQuery()
        logger.info("$resultSet")
        resultSet.next()
    } catch (_: Exception) {
        logger.error("Movie wasn't updated")
        false
    }

    fun deleteMovie(id: Int, user: User) = try {
        val ps = connection.prepareStatement(DataBaseRequests.DELETE_MOVIE)

        ps.setString(1, user.getUsername())
        ps.setInt(2, id)

        val resultSet = ps.executeQuery()

        logger.info("Movie was deleted")
        resultSet.next()
    } catch (e: SQLException) {
        logger.error("Movie wasn't deleted")
        false
    }

    fun loadCollection() = try {
        val collection: ConcurrentHashSet<Movie> = ConcurrentHashSet()

        val ps = connection.prepareStatement(DataBaseRequests.GET_MOVIES)
        val resultSet = ps.executeQuery()
        while (resultSet.next()) {
            collection.add(
                Movie(
                    resultSet.getString("name"),
                    Coordinates(
                        resultSet.getFloat("coord_x"),
                        resultSet.getDouble("coord_y")
                    ),
                    resultSet.getObject("oscarsCount") as Long?,
                    resultSet.getInt("length"),
                    MovieGenre.valueOf(resultSet.getString("genre")),
                    if (resultSet.getString("rating").isNotEmpty())
                        MpaaRating.valueOf(resultSet.getString("rating"))
                                else
                                    null
                                            ,
                    Person(
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_height"),
                        Color.valueOf(resultSet.getString("person_hair_color")),
                        if (resultSet.getString("person_nationality").isNotEmpty())
                            Country.valueOf(resultSet.getString("person_nationality"))
                        else
                            null
                        ),
                    resultSet.getInt("id").toLong(),
                    resultSet.getDate("creation_date").toLocalDate(),
                    resultSet.getString("owner_login")
                )
            )
        }
        logger.info("Collection was downloaded")
        collection
    } catch (e: Exception) {
        logger.error("Collection wasn't downloaded")
        ConcurrentHashSet()
    }
}