package com.app.androidhomework.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentMapBinding
import com.app.androidhomework.models.MapModelItem
import com.app.androidhomework.utils.bitmapDescriptorFromVector
import com.app.androidhomework.utils.showDialogMessages
import com.app.androidhomework.viewmodel.MapViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.collections.ArrayList


class MapFragment : BaseFragment<FragmentMapBinding>(), View.OnClickListener, OnMapReadyCallback {

    private var TAG: String = MapFragment::class.java.simpleName
    private var lastClickTime: Long = 0
    override val layoutId: Int = R.layout.fragment_map
    lateinit var viewModel: MapViewModel
    private var mapModelItem: ArrayList<MapModelItem> = ArrayList()
    private var mapFragment: SupportMapFragment? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var currentLocation: Location? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var latLng: LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    requireActivity().showDialogMessages(
                        "Are you sure you want to exit?",
                        "Exit App",
                        "Yes", "No",
                        listener = {
                            when (it.id) {
                                R.id.txt_positive -> {
                                    session.setStringDataByKey("noti", "")
                                    requireActivity().finish()
                                }
                            }
                        },
                        showBothButton = true,
                        setCanceledOnTouchOutside = false,
                        setCancelable = false,
                        isShowRedColor = true
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        binding.customToolbar.tvTitle.text = "Map"
        binding.customToolbar.tvTitle.gravity = Gravity.CENTER

        observeViewModel()


        Handler().postDelayed({
            initView()
        }, 200)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun initView() {
        viewModel.MapAPI(session.getAuthorizedUser()!!.authentication_token)
    }

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        getLocationAccess(googleMap)

        if (mapModelItem != null) {
            for (i in mapModelItem.indices) {
                googleMap!!.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                    override fun getInfoContents(p0: Marker?): View? {
                        return null
                    }

                    override fun getInfoWindow(p0: Marker?): View? {
                        var v: View? = null
                        try {

                            // Getting view from the layout file info_window_layout
                            v = layoutInflater.inflate(R.layout.custom_marker, null)

                            // Getting reference to the TextView to set latitude
                            v!!.findViewById<View>(R.id.rowItemView) as CardView
                            val tvMarkerTitle =
                                v.findViewById<View>(R.id.tvMarkerTitle) as TextView
                            val input = p0?.title.toString()
                            val output = input.substring(0, 1)
                                .toUpperCase(Locale.getDefault()) + input.substring(1)
                            tvMarkerTitle.text = output
                            val tvMarkerSinp = v.findViewById<View>(R.id.tvVoteCount) as TextView
                            tvMarkerSinp.text = p0?.snippet


                        } catch (ev: Exception) {
                            Log.e(TAG, ev.message.toString())
                        }
                        return v
                    }

                })

                if (mapModelItem != null) {
                    val marker =
                        LatLng(mapModelItem[i].lat, mapModelItem[i].lng)
                    googleMap.addMarker(
                        MarkerOptions().position(marker).title(mapModelItem[i].vehicle_make)
                            .snippet(mapModelItem[i].vehicle_pic_absolute_url).icon(
                                bitmapDescriptorFromVector(
                                    requireContext(),
                                    R.drawable.ic_marker_circle
                                )
                            )
                    ).showInfoWindow()
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                mapModelItem[i].lat.toDouble(),
                                mapModelItem[i].lng.toDouble()
                            ), 0.01f
                        )
                    )
                }

            }

        }

//        It's for current location'

    }

    private fun getLocationAccess(googleMap: GoogleMap?) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap!!.isMyLocationEnabled = true
            getLocationUpdates(googleMap)
            startLocationUpdates(googleMap)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_FINE_LOCATION
            )
        }
    }

    private fun startLocationUpdates(googleMap: GoogleMap?) {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_FINE_LOCATION
            )
            return
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest!!, mLocationCallback!!, null)

    }

    private fun getLocationUpdates(googleMap: GoogleMap?) {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.interval = 5000 //5 seconds
        mLocationRequest!!.fastestInterval = 3000 //3 seconds
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest!!.smallestDisplacement = 0.1F //1/10 meter

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        currentLocation = location
                        latLng = LatLng(location.latitude, location.longitude)
//                        viewModel.MapAPI(session.getAuthorizedUser()!!.authentication_token)

                        //zoom to current position:
                        val cameraPosition =
                            CameraPosition.Builder().target(latLng!!).zoom(14.0f).build()
                        googleMap!!.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                cameraPosition
                            )
                        )
                    }
                }
            }
        }

    }

    fun observeViewModel() {
        viewModel.loadingProgressBar.observe(requireActivity(), {
            it.let {
                if (it) {
                    showProgress()
                } else {
                    hideProgress()
                }

            }
        })

        viewModel.myPostData.observe(requireActivity(), {
            it.let {
                Log.e("TAG", it.toString())
                mapModelItem = it
                val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
                mapFragment!!.getMapAsync(this)

            }
        })

    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111
    }

}