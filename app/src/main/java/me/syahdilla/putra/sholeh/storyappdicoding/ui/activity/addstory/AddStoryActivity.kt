package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.addstory

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityAddStoryBinding
import me.syahdilla.putra.sholeh.storyappdicoding.isUITest
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog.LoadingDialog
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import me.syahdilla.putra.sholeh.storyappdicoding.utils.*
import me.syahdilla.putra.sholeh.storyappdicoding.utils.image.CameraManager
import me.syahdilla.putra.sholeh.storyappdicoding.utils.image.GalleryManager
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File

class AddStoryActivity: BaseActivity<ActivityAddStoryBinding>(ActivityAddStoryBinding::inflate) {

    private val galleryManager: GalleryManager = get { parametersOf(mThis) }
    private val cameraManager: CameraManager = get { parametersOf(mThis) }

    private val viewModel: AddStoryViewModel by viewModel()
    private val loading: LoadingDialog by inject()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private var location: Location? = null

    override var showTopLeftBackButton = true

    override suspend fun onInitialize(savedInstanceState: Bundle?) = with(binding) {

        title = getString(R.string.title_add_story_)

        safeLaunch {
            viewModel.state.flowWithLifecycle(lifecycle).distinctUntilChanged().collectLatest {
                if (it != DefaultEvent.InProgress) loading.close()
                when(it) {
                    is DefaultEvent.Failure -> Toast.makeText(mThis, it.message, Toast.LENGTH_SHORT).show()
                    DefaultEvent.Success -> {
                        Toast.makeText(mThis, getString(R.string.add_story_uploaded), Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()
                    }
                    DefaultEvent.InProgress -> loading.show(mThis)
                    else -> {}
                }
            }
        }

        btnCamera.setOnClickListener {
            safeRunOnce(45) {
                val result = cameraManager.startCamera() ?: return@safeRunOnce
                viewModel.photoFile = result
                photo.setImageBitmap(result.readAsImage)
            }
        }

        btnGallery.setOnClickListener {
            safeRunOnce(44) {
                val result = galleryManager.selectFromGallery() ?: return@safeRunOnce
                viewModel.photoUri = result
                photo.setImageURI(result)
            }
        }

        btnUpload.setOnClickListener {
            if (!viewModel.isPhotoAvailable && !viewModel.isPhotoFileAvailable)
                return@setOnClickListener Toast.makeText(mThis, getString(R.string.add_story_empty_image), Toast.LENGTH_SHORT).show()

            if (description.getText.isEmpty())
                return@setOnClickListener Toast.makeText(mThis, getString(R.string.add_story_empty_description), Toast.LENGTH_SHORT).show()

            viewModel.upload(
                description = description.getText,
                lat = location?.latitude?.toFloat(),
                lon = location?.longitude?.toFloat()
            )

        }

        showLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLastLocation()
                location = null
                return@setOnCheckedChangeListener
            }

            location = null
        }

        if (viewModel.isPhotoAvailable)
            photo.setImageURI(viewModel.photoUri)

        if (viewModel.isPhotoAvailable)
            photo.setImageURI(viewModel.photoUri)
        if (viewModel.isPhotoFileAvailable)
            photo.setImageBitmap(viewModel.photoFile.readAsImage)


        if (!isUITest && animationsEnabled()) animateViews()
    }

    private fun animateViews() = with(binding) {
        root.animateChildViews {
            animateTogether(it.animateBounce(), it.animateFade())
        }.playSequentially()
    }

    private val File.readAsImage: Bitmap
        get() = BitmapFactory.decodeFile(absolutePath)



    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                    logger.error { "No location access guaranteeddd!" }
                    binding.showLocation.isChecked = false
                }
            }
        }


    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (hasPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location == null) {
                    Toast.makeText(
                        this@AddStoryActivity,
                        "Your location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addOnSuccessListener
                }

                this.location = location

            }
            return
        }
        requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun String.hasPermission() = ContextCompat.checkSelfPermission(
        this@AddStoryActivity,
        this
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasPermission() = Manifest.permission.ACCESS_COARSE_LOCATION.hasPermission()// && Manifest.permission.ACCESS_FINE_LOCATION.hasPermission()


}