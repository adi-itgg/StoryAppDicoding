package me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import me.syahdilla.putra.sholeh.storyappdicoding.customLogger
import kotlin.coroutines.resume

/**
 * Gallery manager should be inject immediately
 */
class GalleryManager(
    private val activity: AppCompatActivity
) {

    private val logger by customLogger()

    private var imageResult: CancellableContinuation<Uri?>? = null
    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        logger.debug { "Received result from intent with code ${result.resultCode}" }
        if (result.resultCode != RESULT_OK)
            return@registerForActivityResult onCompleted(null)

        onCompleted(result.data?.data)
    }

    private fun onCompleted(result: Uri?) {
        if (imageResult?.isCancelled == true) imageResult = null
        imageResult?.resume(result)
        imageResult = null
    }

    suspend fun selectFromGallery() =  suspendCancellableCoroutine {
        val intent = Intent(ACTION_GET_CONTENT).setType("image/*")
        val chooser = Intent.createChooser(intent, "Choose a Picture")

        imageResult = it
        it.invokeOnCancellation { imageResult = null }
        launcher.launch(chooser)
        logger.debug { "Waiting image uri from intent..." }
    }

}