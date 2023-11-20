import common.entities.MovieManager
import net.*

class ServerController(private val movieManager: MovieManager, private val server: UDP) {
    fun run(line: String): Boolean {
        if (line == "exit") {
            server.stop()
            return true
        }
        if (line == "save")
            movieManager.save()
        return false
    }
}