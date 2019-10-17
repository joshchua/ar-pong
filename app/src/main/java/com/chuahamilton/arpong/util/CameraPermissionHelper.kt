package com.chuahamilton.arpong.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object CameraPermissionHelper {
    private const val CAMERA_PERMISSION_CODE = 0
    private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

    fun hasCameraPermission(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(CAMERA_PERMISSION), CAMERA_PERMISSION_CODE
        )
    }

    fun isCameraPermissionGranted(activity: Activity): Boolean {
        if (!hasCameraPermission(activity)) {
            requestCameraPermission(activity)
            return false
        }
        return true
    }
}