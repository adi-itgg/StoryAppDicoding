package me.syahdilla.putra.sholeh.storyappdicoding

import me.syahdilla.putra.sholeh.storyappdicoding.data.User
import java.text.SimpleDateFormat
import java.util.*

object UserManager {

    var user: User? = null

    /**
     * get current user session
     *
     * @see user
     * @throws NullPointerException
     */
    val session: User
        get() = user as User

}

val sdfParser
    get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
val sdfDisplayer
    get() = SimpleDateFormat("HH:mm:ss, d MMM yyyy", Locale.getDefault())