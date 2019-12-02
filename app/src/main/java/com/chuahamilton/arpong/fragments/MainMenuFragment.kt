package com.chuahamilton.arpong.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.chuahamilton.arpong.ARPongGameActivity
import com.chuahamilton.arpong.R
import com.chuahamilton.arpong.utils.DifficultyLevel
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenuFragment : Fragment() {

    private var username = ""
    private var difficultyLevel: DifficultyLevel = DifficultyLevel.EASY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        difficultyLevelText.text = getString(R.string.difficulty_level, "Easy")
        difficultyLevel = DifficultyLevel.EASY

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
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                when (progress) {
                    0 -> {
                        difficultyLevelText.text = getString(R.string.difficulty_level, "Easy")
                        difficultyLevel = DifficultyLevel.EASY
                    }
                    1 -> {
                        difficultyLevelText.text = getString(R.string.difficulty_level, "Normal")
                        difficultyLevel = DifficultyLevel.NORMAL
                    }
                    2 -> {
                        difficultyLevelText.text = getString(R.string.difficulty_level, "Hard")
                        difficultyLevel = DifficultyLevel.HARD
                    }
                }
            }
        })
    }

    private fun initializeButtons() {
        newGameBtn.setOnClickListener {
            val arPongGameIntent = Intent(context!!, ARPongGameActivity::class.java)
            arPongGameIntent.putExtra("DIFFICULTY", difficultyLevel)
            startActivity(arPongGameIntent)
        }
    }
}
