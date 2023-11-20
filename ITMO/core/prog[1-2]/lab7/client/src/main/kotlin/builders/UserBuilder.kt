package client.builders

import client.console.ConsoleManager
import common.entities.*
import common.*

/**
 * Movie builder representative class
 */
object UserBuilder: Builder<User> {
    override fun build(): User {
        return User(
            username = setUsername(),
            password=setPassword()
        )
    }
    private fun setUsername(): String {
        var username: String
        while (true) {
            try {
                ConsoleManager.consolePrint("Input login:")

                username = ConsoleManager.getNextLine().trim()
                User.checkUsernameRestrictions(username)
                break
            } catch (e: EmptyStringException) {
                ConsoleManager.consolePrint(e.message!! + "\n")
            } catch (e: Exception) {
                ConsoleManager.consolePrint("Unresolved error\n")
            }
        }
        return username
    }

    private fun setPassword(): String {
        var password: String
        while (true) {
            try {
                ConsoleManager.consolePrint("Input password(at least 4 symbols):")

                password= ConsoleManager.getNextLine().trim()
                User.checkPasswordRestrictions(password)
                break
            } catch (e: EmptyStringException) {
                ConsoleManager.consolePrint(e.message!! + "\n")
            } catch (e: Exception) {
                ConsoleManager.consolePrint("Unresolved error\n")
            }
        }
        return password
    }
}