package net.appitiza.task.ui.mapdisplay

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.collect.ImmutableMap
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import net.appitiza.task.R
import net.appitiza.task.ui.setlocation.LocationPublishMapAdapter
import net.appitiza.task.ui.setlocation.LocationPublishPnCallback
import net.appitiza.task.utility.PUBLISH_CHANNEL_NAME
import net.appitiza.task.utility.PUBNUB_PUBLISH_KEY
import net.appitiza.task.utility.PUBNUB_SUBSCRIBE_KEY
import net.appitiza.task.utility.SUBSCRIBE_CHANNEL_NAME
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MapDisplayActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var pubNub: PubNub
    private var userName: String? = null

    private var executorService: ScheduledExecutorService? = null
    private var random: Random? = null
    private var startTime: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_display)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setPubNub()
    }

    fun setPubNub(pubNub: PubNub) {
        this.pubNub = pubNub
        this.userName = this.pubNub.configuration.uuid
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        this.pubNub?.addListener(LocationPublishPnCallback(LocationPublishMapAdapter(this, mMap), PUBLISH_CHANNEL_NAME))
        this.pubNub?.subscribe()?.channels(Arrays.asList(PUBLISH_CHANNEL_NAME))?.execute()

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
