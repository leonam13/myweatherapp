package com.example.leoweatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder


abstract class LocationServicesFragment : Fragment() {

    abstract var locationCallback: LocationCallback

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var permissionsRequired = arrayOf<String>()

    val locationRequest: LocationRequest = LocationRequest.create().apply {
        //numUpdates = 1
        interval = 4000
        fastestInterval = 2000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    //To handle permissions result
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResult ->
        if (checkPermissionResult(permissionResult)) checkLocationServices()
        else handlePermissionDenied()
    }

    //To handle location services exception result
    private val locationServicesResolution =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK)
                requestLocation()
            else handleLocationServiceDenied()
        }

    private fun handleLocationServiceDenied() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(getString(R.string.location_services_dialog_resolution_title))
            setMessage(getString(R.string.location_services_dialog_resolution_message))
            setPositiveButton(getString(R.string.location_services_dialog_resolution_positive_button)) { _, _ -> checkLocationServices() }
            setCancelable(false)
        }.show()
    }

    fun initRequireLocationFlow(
        locationPermissions: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) {
        permissionsRequired = locationPermissions
        locationPermissionRequest.launch(permissionsRequired)
    }

    private fun checkPermissionResult(permissionResult: Map<String, Boolean>): Boolean {
        for (p in permissionsRequired) {
            if (permissionResult[p] == false) return false
        }
        return true
    }

    private fun shouldDisplayWarning(): Boolean {
        for (p in permissionsRequired) {
            if (shouldShowRequestPermissionRationale(p)) return true
        }
        return false
    }

    private fun handlePermissionDenied() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(getString(R.string.location_services_dialog_not_granted_title))
            setMessage(getString(R.string.location_services_dialog_not_granted_message))
            setPositiveButton(getString(R.string.location_services_dialog_not_granted_positive_button)) { _, _ ->
                if (shouldDisplayWarning()) initRequireLocationFlow()
                else openSettings()
            }
            setCancelable(false)
        }.show()
    }

    private fun openSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).run {
            data = Uri.fromParts("package", requireContext().packageName, null)
            startActivity(this)
        }
    }

    private fun checkLocationServices() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())

        client.checkLocationSettings(builder.build())
            .addOnSuccessListener { requestLocation() }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intent = IntentSenderRequest.Builder(exception.resolution).build()
                        locationServicesResolution.launch(intent)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        sendEx.printStackTrace()
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}