package me.syahdilla.putra.sholeh.storyappdicoding

import android.util.Log
import me.syahdilla.putra.sholeh.storyappdicoding.utils.tryRun
import kotlin.reflect.KClass

class CustomLogger(
    private val tag: String
) {

    constructor(clazz: KClass<*>) : this(clazz.java.simpleName)
    constructor(clazz: Class<*>) : this(clazz.simpleName)

    enum class Level(val value: Int) {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        ASSERT(7)
    }

    private val testFormat = "%lvl %tag: [%thread] %logs"
    private val format = "[%thread] %logs"

    private var isTest = false

    private val thread
        get() = with(Thread.currentThread().name) {
        foreground(if (this == "main") Color.BLUE else Color.LIGHT_GRAY)
    }

    private fun String.asColoredLogs(level: Level) =
        when(level) {
            Level.VERBOSE -> this
            Level.DEBUG -> foreground(Color.LIGHT_MAGENTA)
            Level.INFO -> foreground(Color.LIGHT_BLUE)
            Level.WARN -> foreground(Color.LIGHT_YELLOW)
            Level.ERROR -> foreground(Color.LIGHT_RED)
            Level.ASSERT -> foreground(Color.RED)
        }

    private fun sendLog(level: Level, logs: String) {
        if (isTest) return sendLogsTest(level, logs)
        tryRun(true) {
            sendLogsAndroid(level, logs)
        }.onFailure {
            isTest = true
            sendLogsTest(level, logs)
        }
    }

    private fun sendLogsAndroid(level: Level, logs: String) {
        Log.println(level.value, tag, format
            .replace("%thread", Thread.currentThread().name)
            .replace("%logs", logs)
        )
    }

    private fun sendLogsTest(level: Level, logs: String) {
        val lvl = with(" " + level.name[0].toString() + " ") {
            when (level) {
                Level.VERBOSE -> foreground(Color.WHITE).background(Color.LIGHT_GRAY)
                Level.DEBUG -> foreground(Color.WHITE).background(Color.GREEN)
                Level.INFO -> foreground(Color.WHITE).background(Color.BLUE)
                Level.WARN -> foreground(Color.WHITE).background(Color.YELLOW)
                Level.ERROR -> foreground(Color.WHITE).background(Color.RED)
                Level.ASSERT -> foreground(Color.MAGENTA).background(Color.RED)
            }
        }
        println(testFormat.replace("%lvl", lvl)
            .replace("%thread", thread)
            .replace("%logs", logs.asColoredLogs(level))
            .replace("%tag", tag))
    }

    private fun sendLog(level: Level, logs: List<String?>) {
        if (logs.size == 1) return sendLog(level, logs[0].toString())
        val sb = StringBuilder()
        logs.forEach {
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append(it)
        }
        sendLog(level, sb.toString())
        sb.setLength(0)
    }

    fun verbose(vararg logs: String?) =
        sendLog(Level.VERBOSE, logs.toList())
    inline fun verbose(logs: () -> String) =
        verbose(logs())

    fun debug(vararg logs: String?) =
        sendLog(Level.DEBUG, logs.toList())
    inline fun debug(logs: () -> String) =
        debug(logs())

    fun info(vararg logs: String?) =
        sendLog(Level.INFO, logs.toList())
    inline fun info(logs: () -> String) =
        info(logs())

    fun warn(vararg logs: String?) =
        sendLog(Level.WARN, logs.toList())
    inline fun warn(logs: () -> String) =
        warn(logs())

    fun error(vararg logs: String?) =
        sendLog(Level.ERROR, logs.toList())
    inline fun error(logs: () -> String) =
        error(logs())

    fun assert(vararg logs: String?) =
        sendLog(Level.ASSERT, logs.toList())
    inline fun assert(logs: () -> String) =
        assert(logs())

}

inline fun<reified T: Any> T.customLogger() = lazy(LazyThreadSafetyMode.NONE) {
    CustomLogger(this::class)
}

private const val ESCAPE = '\u001B'
private const val RESET = "$ESCAPE[0m"
private const val BG_JUMP = 10

enum class Color(baseCode: Int) {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    LIGHT_GRAY(37),

    DARK_GRAY(90),
    LIGHT_RED(91),
    LIGHT_GREEN(92),
    LIGHT_YELLOW(93),
    LIGHT_BLUE(94),
    LIGHT_MAGENTA(95),
    LIGHT_CYAN(96),
    WHITE(97);

    val foreground: String = "$ESCAPE[${baseCode}m"
    val background: String = "$ESCAPE[${baseCode + BG_JUMP}m"
}

fun String.foreground(color: Color) = color(this, color.foreground)
fun String.background(color: Color) = color(this, color.background)
private fun color(string: String, ansiString: String) = "$ansiString$string$RESET"
