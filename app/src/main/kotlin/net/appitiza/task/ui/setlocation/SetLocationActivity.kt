package net.appitiza.task.ui.setlocation

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.common.collect.ImmutableMap
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import kotlinx.android.synthetic.main.activity_set_location.*
import net.appitiza.task.BuildConfig
import net.appitiza.task.R
import net.appitiza.task.utility.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SetLocationActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener , OnMapReadyCallback {


    private val TAG = "CreateSite"
    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    private lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()
    private val FASTEST_INTERVAL: Long = 2000

    private lateinit var locationManager: LocationManager
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private var pubNub: PubNub? = null
    private var userName: String? = null
    private var random: Random? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_set) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setPubNub()


    }

    private fun checkPermissions(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                    btn_setlocation_start,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, View.OnClickListener {
                        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                REQUEST_PERMISSIONS_REQUEST_CODE)
                    })
                    .show()
        } else {
            Log.i(TAG, "Requesting permission")

            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)

        }
    }





    override fun onStop() {
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
        super.onStop()
    }

    private fun initialize() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!checkPermissions()) {
                requestPermissions()
            } else {
                if (checkLocation()) {
                    fetchGPS()
                }

            }

        } else {
            if (checkLocation()) {
                fetchGPS()
            }
        }

    }

    private fun fetchGPS() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager

            mGoogleApiClient.connect()
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                if (checkLocation()) {
                    fetchGPS()
                }
            } else {
                // Permission denied.
                Snackbar.make(btn_setlocation_start,
                        R.string.permission_rationale,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, View.OnClickListener {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri: Uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        })
                        .show()

            }
        }

    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient.connect();
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.errorCode)
    }

    override fun onLocationChanged(location: Location) {


        var msg = "Updated Location: Latitude " + location.latitude.toString() + location.longitude
        mLocation = location
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        val message = getNewLocationMessage(userName, location)

        this.pubNub?.publish()?.channel(PUBLISH_CHANNEL_NAME)?.message(message)?.async(
                object : PNCallback<PNPublishResult>() {
                    override fun onResponse(result: PNPublishResult, status: PNStatus) {
                        try {
                            if (!status.isError) {
                                Log.v(TAG, "publish(" + JsonUtil.asJson(result) + ")")
                            } else {
                                Log.v(TAG, "publishErr(" + status.toString() + ")")
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
        )

    }

    private fun getNewLocationMessage(userName: String?, location: Location): ImmutableMap<String, String> {
        return ImmutableMap.of("who", userName, "lat", java.lang.Double.toString(location.latitude), "lng", java.lang.Double.toString(location.longitude))
    }
    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return
        }


        startLocationUpdates()

        val fusedLocationProviderClient:
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location

                    }
                })
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initialize()

    }

    private fun setPubNub() {
        this.random = Random()
        this.userName = "testUser"
        this.pubNub = initPubNub(userName)
        this.userName = pubNub?.configuration?.uuid
    }

    fun initPubNub(userName: String?): PubNub {
        val pnConfiguration = PNConfiguration()
        pnConfiguration.publishKey = PUBNUB_PUBLISH_KEY
        pnConfiguration.subscribeKey = PUBNUB_SUBSCRIBE_KEY
        pnConfiguration.isSecure = true
        pnConfiguration.uuid = userName

        return PubNub(pnConfiguration)
    }

}
