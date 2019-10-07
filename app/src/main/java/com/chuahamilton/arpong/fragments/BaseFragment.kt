package com.chuahamilton.arpong.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chuahamilton.arpong.R
import com.chuahamilton.arpong.viewmodels.MainViewModel
import com.chuahamilton.arpong.MainActivity
import android.widget.Toast
import android.content.Context.ACTIVITY_SERVICE
import android.app.ActivityManager
import android.app.Activity
import android.util.Log
import android.view.*
import com.chuahamilton.arpong.util.CameraPermissionHelper
import com.google.ar.core.ArCoreApk


class BaseFragment : Fragment() {

    private val TAG = MainActivity::class.java.simpleName
    private val MIN_OPENGL_VERSION = 3.0

    // Tracks if we have already triggered an installation request.
    private var install_requested_: Boolean = false

    companion object {
        fun newInstance() = BaseFragment()
    }

    private lateinit var viewModel: MainViewModel


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDeviceOrFinish(activity!!)) {
            return
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.base_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example of using Glide to show a GIF
        // Glide.with(this).load(R.drawable.scanning_phone).into(scanning_phone_gif)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        val openGlVersionString =
            (activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager).deviceConfigurationInfo.glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        if (!CameraPermissionHelper.hasCameraPermission(activity!!)) {
            Toast.makeText(
                context!!,
                getString(R.string.camera_permission_toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
