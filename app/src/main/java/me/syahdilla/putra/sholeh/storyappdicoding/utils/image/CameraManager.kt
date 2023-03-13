package me.syahdilla.putra.sholeh.storyappdicoding.utils.image

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import me.syahdilla.putra.sholeh.storyappdicoding.utils.customLogger
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeLaunch
import org.koin.core.annotation.Factory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume

/**
 * Camera manager should be inject immediately
 */
@Factory
class CameraManager(
    private val imageManager: ImageManager,
    private val activity: AppCompatActivity
) {

    private val logger by customLogger()

    private var imageResult: CancellableContinuation<File?>? = null
    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        logger.debug { "Launcher has result with code ${result.resultCode}" }
        if (result.resultCode != RESULT_OK)
            logger.debug { "Launcher result OK and write to file ${photoFile?.path}" }
        onCompleted(result.resultCode == RESULT_OK)
    }

    private val generateFileNameTimeStamp: String
        get() = SimpleDateFormat(
            "dd-MMM-yyyy",
            Locale.getDefault()
        ).format(System.currentTimeMillis())

    private fun createCustomTempFile(): File {
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(generateFileNameTimeStamp, ".jpg", storageDir)
    }

    private fun onCompleted(isSuccess: Boolean) {
        if (activity.isDestroyed) {
            imageResult = null
            return
        }
        activity.safeLaunch {
            photoFile = if (isSuccess)
                photoFile?.let {
                    imageManager.compressImage(it).apply { it.delete() }
                }
            else null
            if (imageResult?.isCancelled == true) imageResult = null
            imageResult?.resume(photoFile)
            imageResult = null
        }
    }

    private var photoFile: File? = null
    suspend fun startCamera() = suspendCancellableCoroutine { conti ->
        imageResult = conti
        conti.invokeOnCancellation { imageResult = null }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(activity.packageManager)

        val photoUri = createCustomTempFile().run {
            photoFile = this
            FileProvider.getUriForFile(activity,
                "me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory",
                this)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        launcher.launch(intent)
        logger.debug { "Waiting image uri from intent..." }
    }

}