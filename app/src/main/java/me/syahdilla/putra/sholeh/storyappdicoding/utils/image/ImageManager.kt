package me.syahdilla.putra.sholeh.storyappdicoding.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.storyappdicoding.utils.customLogger
import me.syahdilla.putra.sholeh.storyappdicoding.sdfDisplayer
import org.koin.core.annotation.Single
import java.io.File
import java.util.*

@Single
class ImageManager(
    private val context: Context
) {

    private val logger by customLogger()

    /**
     * Compress image with max resolution 1280x720 and quality 80%
     *
     * @return compressed [file]
     */
    suspend fun compressImage(file: File): File {
        logger.debug { "Compressing file ${file.path}" }
        val outputFile = File(file.parent as String, "compressed-${file.name}")
        val result = Compressor.compress(context, file) {
            resolution(1280, 720)
            quality(80)
            format(Bitmap.CompressFormat.JPEG)
            size(1_000_000) // 1 MB
            destination(outputFile)
        }
        val compressedSize = file.length().toDouble() - result.length().toDouble()
        val calcPercent = ((compressedSize / file.length()) * 100.0).toInt()
        logger.debug {
            "compress file from ${file.length().toDouble().bytesToReadable()} --> ${result.length().toDouble().bytesToReadable()} ($calcPercent%⇣) - ${result.path}"
        }
        return outputFile
    }

    /**
     * Copy file from uri to local temporary file
     * @return [File] if success, null otherwise
     */
    suspend fun uriToFile(fileUri: Uri): File? = withContext(IO) {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile(sdfDisplayer.format(Calendar.getInstance().time).replace(":",";"), ".jpg", storageDir)
        context.contentResolver.openInputStream(fileUri)?.use { iStream ->
            tempFile.outputStream().use { outStream ->
                val chunk = ByteArray(DEFAULT_BUFFER_SIZE / 2)
                var byte: Int
                while (iStream.read(chunk).also { byte = it } > 0) outStream.write(chunk, 0, byte)
            }
        } ?: run {
            logger.error { "failure write photo to temp file!" }
            return@withContext null
        }

        tempFile
    }

    suspend fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor = withContext(Default) {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
        if (vectorDrawable == null) {
            logger.error("BitmapHelper", "Resource not found")
            return@withContext BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun Double.bytesToReadable() = when {
        this >= 1 shl 30 -> "%.1f GB".format(this / (1 shl 30))
        this >= 1 shl 20 -> "%.1f MB".format(this / (1 shl 20))
        this >= 1 shl 10 -> "%.0f kB".format(this / (1 shl 10))
        else -> "$this bytes"
    }

}