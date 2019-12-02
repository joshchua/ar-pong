package com.chuahamilton.arpong.arpong

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext

fun checkIsSupportedDeviceOrFinish(TAG: String, activity: AppCompatActivity): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        val message = "Sceneform requires Android N or later"
        Log.e(TAG, message)
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        activity.finish()
        return false
    }
    return true
}

suspend fun makeSphereRenderable(
    context: Context,
    radius: Float,
    position: Vector3,
    color: Color
): ModelRenderable = withContext(
    Dispatchers.Main
) {
    val material = MaterialFactory.makeOpaqueWithColor(context, color).await()
    ShapeFactory.makeSphere(radius, position, material)
}

suspend fun makeCubeRenderable(
    context: Context,
    size: Vector3,
    position: Vector3,
    color: Color) = withContext(
        Dispatchers.Main
) {
    val material = MaterialFactory.makeOpaqueWithColor(context, color).await()
    ShapeFactory.makeCube(size, position, material)
}

suspend fun makeViewRenderable(context: Context, viewId: Int) = withContext(Dispatchers.Main) {
    val viewRenderable: ViewRenderable = ViewRenderable.builder().setView(context, viewId).build().await()
    viewRenderable
}