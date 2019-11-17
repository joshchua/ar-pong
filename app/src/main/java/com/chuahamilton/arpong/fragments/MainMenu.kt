package com.chuahamilton.arpong.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chuahamilton.arpong.R
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenu : Fragment() {

    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUsername()
    }

    private fun setUsername(){
        this.username = arguments!!.getString("username")!!
        welcomeText.text = getString(R.string.welcomeMessage, username)
    }

}
