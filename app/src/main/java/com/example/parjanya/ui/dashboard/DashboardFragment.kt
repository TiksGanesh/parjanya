package com.example.parjanya.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parjanya.MainActivity
import com.example.parjanya.R
import com.example.parjanya.model.WeatherRequest
import com.example.parjanya.network.RequestService
import com.google.android.gms.location.*
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel  by viewModel()
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val adapter:DailyWeatherAdapter by lazy {
        DailyWeatherAdapter()
    }

    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        textView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        initRecyclerView(root)
        return root
    }

    private fun initRecyclerView(root: View) {

        recyclerView = root.findViewById(R.id.weatherRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFetchingUserLocation()
        startObservingResponse()
    }

    override fun onPause() {
        super.onPause()
        stopLocationFetch()
    }

    private fun startObservingResponse() {

        dashboardViewModel.apiResponse.observe( viewLifecycleOwner, Observer {

            if (it.isNotEmpty()){
                adapter.updateData(it)
                recyclerView.visibility = View.VISIBLE
                textView.visibility = View.GONE
            }

        })
    }



    private fun startFetchingUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            (activity as MainActivity).showSnackBar(getString(R.string.error_permission))
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null){
                dashboardViewModel.location = it
                dashboardViewModel.fetchData(WeatherRequest(
                    it.latitude.toString(),
                    it.longitude.toString()
                ))
                Log.d("####", "Last Location: $it")
            }else{

                val locationRequest = LocationRequest.create()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                locationRequest.interval = 20 * 1000

                mFusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper())
            }
        }

    }

    private fun stopLocationFetch(){
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback(){

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
            Log.d("####", "Availability: ${p0.isLocationAvailable}")
        }

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            dashboardViewModel.location = result.lastLocation
        }
    }
}