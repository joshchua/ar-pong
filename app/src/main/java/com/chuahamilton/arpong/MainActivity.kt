package com.chuahamilton.arpong

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.chuahamilton.arpong.fragments.LoginFragment
import com.chuahamilton.arpong.services.IntroMusicService
import kotlinx.android.synthetic.main.main_activity.*
import java.lang.Thread.sleep



class MainActivity : AppCompatActivity() {

    private var mainIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        mainIntent = startMusic()

        sleep(1000)

        this.findNavController(nav_host_fragment.id)

        val action =
            SpecifyAmountFragmentDirections
                .actionSpecifyAmountFragmentToConfirmationFragment()
        view.findNavController().navigate(action)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        mainIntent = startMusic()
    }

    override fun onPause() {
        super.onPause()
        stopService(mainIntent)
    }

    private fun startMusic(): Intent{
        val musicService = Intent(this, IntroMusicService::class.java)
        startService(musicService)
        return musicService
    }
}
