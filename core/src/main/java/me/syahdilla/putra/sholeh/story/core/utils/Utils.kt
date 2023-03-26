@file:OptIn(ExperimentalContracts::class)

package me.syahdilla.putra.sholeh.story.core.utils

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.syahdilla.putra.sholeh.story.core.CoreComponent
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * fast run catching
 */
inline fun <reified R> runCatching(block: () -> R): Result<R> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

/**
 * Try run is wrapper from [runCatching] and safe for coroutines
 *
 * @see runCatching
 */
inline fun <reified R> tryRun(noLogs: Boolean = false, block: () -> R): Result<R> =
    runCatching(block).onFailure {
        if (it is CancellationException) throw it
        if (!noLogs) it.printStackTrace()
    }

val json = Json {
    ignoreUnknownKeys = true
}
val prettyJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

/**
 * Convert current object to json
 */
inline fun <reified T : Any> T.asJson(prettyPrint: Boolean = false) = (if (prettyPrint) prettyJson else json).encodeToString(this)

/**
 * Parse json string as object
 */
inline fun <reified T: Any> String.asObject() = json.decodeFromString<T>(this)

/**
 * Initialize view binding
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        bindingInflater.invoke(layoutInflater)
    }

private val logger = CustomLogger("SafeLaunch")
val ignoreErrorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    logger.error("Context $coroutineContext\n${throwable.stackTraceToString()}")
    Toast.makeText(CoreComponent().context, throwable.stackTraceToString(), Toast.LENGTH_SHORT).show()
}
private val safeLaunchContextName = CoroutineName("safeLaunch")

/**
 * Safe launch coroutine job
 */
fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = CoroutineScope(
    coroutineContext +
            ignoreErrorHandler +
            safeLaunchContextName
).launch(context, start, block)

fun LifecycleOwner.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.safeLaunch(context, start, block)

fun ViewModel.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.safeLaunch(context, start, block)

private val jobs = WeakHashMap<CoroutineContext, Job>()
/**
 * Safe run once job
 *
 * don't use in unknown dispatcher!
 * @param cancelAndWait cancel and wait current running job
 */
fun CoroutineScope.safeRunOnce(
    id: Int = -1,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    cancelAndWait: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val cContext = coroutineContext.plus(CoroutineName("OnceId-$id"))
    var isCancelled = false
    jobs[cContext]?.let {
        if (it.isActive) {
            if (cancelAndWait) {
                it.cancel()
                isCancelled = true
            } else return it
        }
    }

    return safeLaunch(context, start) {
        jobs[cContext]?.let {
            if (it.isActive && isCancelled) it.cancelAndJoin()
            if (!it.isCompleted && isCancelled) it.join()
        }
        block()
    }.apply {
        jobs[cContext] = this
        invokeOnCompletion {
            jobs.remove(cContext)
        }
    }
}

/**
 * Safe run once job
 *
 * don't use in unknown dispatcher!
 * @param cancelAndWait cancel and wait current running job
 */
fun LifecycleOwner.safeRunOnce(
    id: Int = -1,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    cancelAndWait: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.safeRunOnce(id, context, start, cancelAndWait, block)

fun ViewModel.safeRunOnce(
    id: Int = -1,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    cancelAndWait: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.safeRunOnce(id, context, start, cancelAndWait, block)

val isUITest by lazy {
    tryRun {
        Thread.currentThread().stackTrace.any {
            it.className == "androidx.test.runner.AndroidJUnitRunner" ||
                    it.className == "androidx.test.runner.MonitoringInstrumentation"
        }
    }.getOrDefault(false)
}