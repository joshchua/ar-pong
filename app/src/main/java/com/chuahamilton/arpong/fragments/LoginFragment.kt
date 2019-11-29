package com.chuahamilton.arpong.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chuahamilton.arpong.R
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private var bundle = Bundle()

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Override
    override fun onResume() {
        super.onResume()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeButtons()
    }

    private fun initializeButtons() {
        loginBtn.setOnClickListener {
            this.bundle.putString("username", usernameEditText.text.toString())

            val mainMenuFragment = MainMenuFragment()
            mainMenuFragment.arguments = bundle

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainMenuFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}
