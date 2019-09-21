package com.chuahamilton.arpong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chuahamilton.arpong.fragments.BaseFragment
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        sleep(1000)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BaseFragment())
                .commitNow()
        }
    }
}
