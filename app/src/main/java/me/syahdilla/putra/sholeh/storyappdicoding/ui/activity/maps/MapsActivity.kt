package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.maps

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.ktor.http.parsing.*
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import me.syahdilla.putra.sholeh.story.core.utils.safeLaunch
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityMapsBinding
import org.koin.android.ext.android.inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val logger by customLogger()
    private val viewModel: MapViewModel by inject()

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var markerIcon: BitmapDescriptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Jakarta, Indonesia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        with(googleMap.uiSettings) {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        tryRun {
            val isSuccess = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_styled_aubergine
                )
            )
            if (!isSuccess) throw ParseException("failure parse map json!")
        }.onFailure {
            logger.error { "failure parse map style!" }
        }

        // Add a marker in Jakarta and move the camera
        val jakarta = LatLng(-6.200000, 106.816666)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 5f))


        safeLaunch {
            markerIcon = viewModel.getMarkIcon()

            for (i in 1..15) {
                val res = viewModel.getStories(page = i, size = 10).getOrNull() ?: break
                if (res.error || res.listStory.isEmpty()) break

                logger.debug { "Loaded page $i" }
                res.listStory.forEach { story ->
                    addMarker(story)
                }

            }
        }

    }

    private fun addMarker(story: Story) {
        val latLng = LatLng(story.lat as Double, story.lon as Double)
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(story.name)
                .snippet("Lat: ${latLng.latitude} Long: ${latLng.longitude}")
                .icon(markerIcon)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?) = with(menu) {
        menuInflater.inflate(R.menu.map_menu, this)
        true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.normal_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.satellite_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        R.id.hybrid_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}