package com.example.parjanya.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.parjanya.MainActivity
import com.example.parjanya.R
import com.example.parjanya.core.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private val permissionRequestCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (isPermissionAlreadyGranted()){
            navigateToDashboard()
        }else{
            setOnClickListener()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()){
            val fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            val coarseLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
            if (fineLocationAccepted && coarseLocationAccepted){
                navigateToDashboard()
            }else{
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    /**
     * Set listener on button
     */
    private fun setOnClickListener() {
        buttonRequestPermission.setOnClickListener {
            requestLocationPermission()
        }
    }

    /**
     * Check if permission already granted
     */
    private fun isPermissionAlreadyGranted(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        return PackageManager.PERMISSION_GRANTED == permissionStatus
    }

    /**
     * Request permission for location
     */
    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),
            permissionRequestCode
        )
    }


    /**
     * Navigate to dashboard
     */
    private fun navigateToDashboard(){
        val dashboardIntent = Intent(this, MainActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }
}