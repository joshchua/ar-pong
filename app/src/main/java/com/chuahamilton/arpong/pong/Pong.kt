package com.chuahamilton.arpong.pong

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.chuahamilton.arpong.R
import com.chuahamilton.arpong.utils.DifficultyLevel
import kotlinx.android.synthetic.main.game_winner_dialog.*
import kotlinx.android.synthetic.main.game_winner_dialog.view.*
import kotlin.math.abs
import kotlin.random.Random

class Pong(context: Context, private val difficultyLevel: DifficultyLevel) {
    val ball = Ball(0.0f, 0.0f, 0.00f, 0.01f, 0.05f)
    val player1Paddle = Paddle(0.0f, 0.5f, 0.2f, 0.1f)
    val player2Paddle = Paddle(0.0f, -0.5f, 0.2f, 0.1f)

    private val leftWall = -0.5f
    private val rightWall = 0.5f
    private val middleOfBoard = 0.0f
    private val context = context

    private val computerPaddleSoundEffect = MediaPlayer.create(context, R.raw.computer_paddle_hit)
    private val playerPaddleSoundEffect = MediaPlayer.create(context, R.raw.player_paddle_hit)

    private val WINNING_SCORE = 10
    var player1Score = 0
    var player2Score = 0

    var player2Stuck = false

    fun handlePlayer1Input(input: Int) {
        when {
            input < 0 -> movePlayer1Paddle(-0.02f)
            input > 0 -> movePlayer1Paddle(0.02f)
        }
    }

    private fun movePlayer1Paddle(dx: Float) {
        player1Paddle.x += dx
        player1Paddle.xSpeed = dx

        val halfPaddleWidth = player1Paddle.width / 2
        if (player1Paddle.x - halfPaddleWidth < leftWall) {
            player1Paddle.x = leftWall + halfPaddleWidth
            player1Paddle.xSpeed = 0.0f
        } else if (player1Paddle.x + halfPaddleWidth > rightWall) {
            player1Paddle.x = rightWall - halfPaddleWidth
            player1Paddle.xSpeed = 0.0f
        }
    }

    fun update() {
        updateBall()
        updatePlayer2()
    }

    private fun updatePlayer2() {

        if (ball.xSpeed < 0) {
            player2Paddle.xSpeed = -0.02f
        } else {
            player2Paddle.xSpeed = 0.02f
        }

        val player2Dist = abs(ball.y - player2Paddle.y)
        if (player2Dist > 0.3) {
            player2Paddle.x = ball.x
            player2Stuck = false
        } else {
            when (difficultyLevel) {
                DifficultyLevel.EASY -> freezePlayer2Paddle(2)
                DifficultyLevel.NORMAL -> freezePlayer2Paddle(4)
                DifficultyLevel.HARD -> freezePlayer2Paddle(8)
            }
        }

        val halfPaddleWidth = player2Paddle.width / 2
        if (player2Paddle.x - halfPaddleWidth < leftWall) {
            player2Paddle.x = leftWall + halfPaddleWidth
            player2Paddle.xSpeed = 0.0f
        } else if (player2Paddle.x + halfPaddleWidth > rightWall) {
            player2Paddle.x = rightWall - halfPaddleWidth
            player2Paddle.xSpeed = 0.0f
        }

    }

    private fun freezePlayer2Paddle(range: Int) {
        val boardWidth = abs(leftWall - rightWall)
        if (!player2Stuck) {
            val dist = boardWidth / range
            val random = Random.nextFloat() * dist
            player2Paddle.x = ball.x - (random / 2)
            player2Stuck = true
        }
    }

    private fun updateBall() {
        ball.x += ball.xSpeed
        ball.y += ball.ySpeed

        handleWallCollision()
        checkForRoundWinner()
        handlePaddleCollision()
    }

    private fun handleWallCollision() {
        if (ball.x - ball.radius < leftWall) {
            ball.x = leftWall + ball.radius
            ball.xSpeed = -ball.xSpeed
        } else if (ball.x + ball.radius > rightWall) {
            ball.x = rightWall - ball.radius
            ball.xSpeed = -ball.xSpeed
        }
    }

    private fun checkForRoundWinner() {
        val player1Wall = player1Paddle.y
        val player2Wall = player2Paddle.y

        if (ball.y > player1Wall) {
            player2Score += 1
            if (player2Score == WINNING_SCORE) {
                stopBall()
                gameOverPopup("CPU")
            } else {
                respawnBall()
            }


        } else if (ball.y < player2Wall) {
            player1Score += 1
            if (player1Score == WINNING_SCORE) {
                stopBall()
                gameOverPopup("Player 1")
            } else {
                respawnBall()
            }
        }
    }

    private fun respawnBall() {
        ball.x = kotlin.math.abs(leftWall + rightWall) / 2
        ball.y = middleOfBoard
        ball.xSpeed = 0.0f
        ball.ySpeed = 0.01f
    }

    private fun stopBall() {
        ball.x = kotlin.math.abs(leftWall + rightWall) / 2
        ball.y = middleOfBoard
        ball.xSpeed = 0.0f
        ball.ySpeed = 0.0f
    }

    private fun restartGame() {
        player1Score = 0
        player2Score = 0

        respawnBall()
    }

    private fun handlePaddleCollision() {
        val topX = ball.x - ball.radius
        val topY = ball.y - ball.radius
        val bottomX = ball.x + ball.radius
        val bottomY = ball.y + ball.radius

        if (topY > middleOfBoard) {
            if (topY < (player1Paddle.y + player1Paddle.height) &&
                bottomY > player1Paddle.y &&
                topX < (player1Paddle.x + player1Paddle.width) &&
                bottomX > player1Paddle.x
            ) {

                playerPaddleSoundEffect.start()

                ball.ySpeed = -0.01f
                ball.xSpeed += player1Paddle.xSpeed / 2

                ball.xSpeed = kotlin.math.min(ball.xSpeed, 0.05f)
                ball.y += ball.ySpeed
            }
        } else {
            if (topY < (player2Paddle.y + player2Paddle.height) &&
                bottomY > player2Paddle.y &&
                topX < (player2Paddle.x + player2Paddle.width) &&
                bottomX > player2Paddle.x
            ) {

                computerPaddleSoundEffect.start()

                ball.ySpeed = 0.01f
                ball.xSpeed += player2Paddle.xSpeed / 2

                ball.xSpeed = kotlin.math.min(ball.xSpeed, 0.05f)
                ball.y += ball.ySpeed
            }
        }
    }

    private fun gameOverPopup(playerName: String) {

        val mDialogView = LayoutInflater.from(context).inflate(R.layout.game_winner_dialog, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setLayout(800, 1300)
        mDialogView.winnerNameText.text = playerName
        Glide.with(mDialogView).load(R.drawable.carltondance).into(mAlertDialog.winningGif)
        mDialogView.playAgainBtn.setOnClickListener {
            restartGame()
            mAlertDialog.dismiss()
        }
    }

}