package common.entities

import common.EmptyStringException
import kotlinx.serialization.Serializable

@Serializable
class User {
    private var username: String
    private var password: String
    constructor(username:String, password: String) {
        checkUsernameRestrictions(username)
        checkPasswordRestrictions(password)
        this.username=username
        this.password=password
    }
    companion object {
        fun checkUsernameRestrictions(name: String) {
            if (name.isEmpty()) throw EmptyStringException("User name can't be empty")
            if (name.contains(",")) throw EmptyStringException("User name can't use symbol ','")
        }

        fun checkPasswordRestrictions(password: String) {
            if (password.isEmpty()) throw EmptyStringException("Password can't be empty")
            if (password.length < 4) throw EmptyStringException("Password is too few")
        }
    }

    fun getUsername() = username

    fun getPassword() = password
}