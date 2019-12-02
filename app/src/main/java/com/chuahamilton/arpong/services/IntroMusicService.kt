package com.chuahamilton.arpong.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.chuahamilton.arpong.R


class IntroMusicService : Service() {
    private lateinit var player: MediaPlayer
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.intro_theme)
        player.isLooping = true // Set looping
        player.setVolume(0.5f, 0.5f)
        player.start()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {
    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }

    override fun onLowMemory() {

    }

    companion object {
        private val TAG: String? = null
    }
}