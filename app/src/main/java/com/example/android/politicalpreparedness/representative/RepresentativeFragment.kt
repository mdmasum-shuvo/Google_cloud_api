package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.BuildConfig
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentRepresentativeBinding
    private val viewModel by viewModels<RepresentativeViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object {
        //TODO: Add Constant for Location request

        private const val REQUEST_LOCATION_PERMISSION = 115
        private const val REQUEST_TURN_DEVICE_LOCATION_ON = 135
    }

    //TODO: Declare ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_representative, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.representativeViewModel = viewModel
        binding.rv.adapter = RepresentativeListAdapter()

        binding.buttonLocation.setOnClickListener {
            if (checkLocationPermissions()) {
                getLocation()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
                )
            }
        }
        return binding.root
    }


    private fun checkLocationPermissions(): Boolean {
        return isPermissionGranted()
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //TODO: Get location from LocationServices
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location

                val address = geoCodeLocation(lastLocation)
                binding.address = address
                val stateText = address.state
                viewModel.searchRepresentetive(address)

                if (stateText != "") {
                    val statesForSpinner = requireContext().resources.getStringArray(R.array.states)
                    var statesAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        statesForSpinner
                    )
                    val spinnerPosition: Int = statesAdapter.getPosition(stateText)
                    binding.state.setSelection(spinnerPosition)
                }

            }
        }
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                // Displays App settings screen.
                Snackbar.make(
                    binding.root,
                    R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.settings) {
                        // Displays App settings screen.
                        startActivity(Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }.show()
            }
        }
    }


}

