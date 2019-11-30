package com.chuahamilton.arpong.pong

import android.content.Context
import android.media.MediaPlayer
import com.chuahamilton.arpong.R

class Pong(private val context: Context) {
    val ball = Ball(0.0f, 0.0f, 0.00f, 0.01f, 0.05f)

    val player1Paddle = Paddle(0.0f, 0.5f, 0.2f, 0.1f)
    val player2Paddle = Paddle(0.0f, -0.5f, 0.2f, 0.1f)

    private val leftWall = -0.5f
    private val rightWall = 0.5f

    private val middleOfBoard = 0.0f

    private val computerPaddleSoundEffect = MediaPlayer.create(context, R.raw.computer_paddle_hit)
    private val playerPaddleSoundEffect = MediaPlayer.create(context, R.raw.player_paddle_hit)

    var player1Score = 0
    var player2Score = 0

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
        player2Paddle.x = ball.x
        if (ball.xSpeed < 0) {
            player2Paddle.xSpeed = -0.02f
        } else {
            player2Paddle.xSpeed = 0.02f
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
            respawnBall()
        } else if (ball.y < player2Wall) {
            player1Score += 1
            respawnBall()
        }
    }

    private fun respawnBall() {
        ball.x = kotlin.math.abs(leftWall + rightWall) / 2
        ball.y = middleOfBoard
        ball.xSpeed = 0.0f
        ball.ySpeed = 0.01f
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
}