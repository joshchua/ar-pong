package com.chuahamilton.arpong.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.chuahamilton.arpong.R
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenu : Fragment() {

    private var username = ""
    private var difficultyLevel = "Easy"
    private var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        difficultyLevelText.text = getString(R.string.difficulty_level, difficultyLevel)

        initializeButtons()
        setUsername()
        setDifficultyLevelListener()

    }

    private fun setUsername() {
        this.username = arguments!!.getString("username")!!
        welcomeText.text = getString(R.string.welcomeMessage, username)
    }

    private fun setDifficultyLevelListener() {
        difficultyBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                when (progress) {
                    0 -> difficultyLevelText.text = getString(R.string.difficulty_level, "Easy")
                    1 -> difficultyLevelText.text = getString(R.string.difficulty_level, "Normal")
                    2 -> difficultyLevelText.text = getString(R.string.difficulty_level, "Hard")
                }
            }
        })
    }

    private fun initializeButtons() {

//        this.bundle.putString("difficultyKey", difficultyLevel)
//        val gameFragment = GameFragment()
//        gameFragment.arguments = bundle

        newGameBtn.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}
