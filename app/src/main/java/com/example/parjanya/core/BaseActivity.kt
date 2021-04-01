package com.example.parjanya.core

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.parjanya.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showInfoSnackBar(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    fun showInternetConnectionSnackBar(view:View){
        Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show();
    }
}