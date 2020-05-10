package com.example.testseekeritunes.view.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.OnKeyListener
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testseekeritunes.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : Activity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityWebView.loadUrl(intent.getStringExtra("collectionViewUrl") ?: "")
        val webSettings: WebSettings = activityWebView.settings
        webSettings.javaScriptEnabled = true
        activityWebView.webViewClient = MyWebClient()
        activityWebView.setOnKeyListener(OnKeyListener { _, keyCode, event ->
            try {
                //This is the filter
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener true
                }

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (activityWebView.canGoBack()) {
                        activityWebView.goBack()
                    } else {
                        onBackPressed()
                    }

                    return@OnKeyListener true
                }
            } catch (e: Exception) {
            }
            false
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionsList: MutableList<String> = ArrayList()
            for (permission in arrayOf(Manifest.permission.INTERNET)) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        permissionsList.add(permission)
                    }
                }
            }

            if (permissionsList.isNotEmpty()) {
                val callBack = 0
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.INTERNET),
                    callBack
                )
            }
        }
    }

    override fun onBackPressed() {
        if (activityWebView.canGoBack()) {
            activityWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private inner class MyWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }
}