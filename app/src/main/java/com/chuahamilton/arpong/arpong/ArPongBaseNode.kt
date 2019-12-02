package com.chuahamilton.arpong.arpong

import android.content.Context
import android.graphics.Color.parseColor
import com.chuahamilton.arpong.R
import com.chuahamilton.arpong.pong.Pong
import com.chuahamilton.arpong.utils.DifficultyLevel
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.ViewRenderable
import kotlinx.android.synthetic.main.arpong_scoreboard.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArPongBaseNode(private val context: Context, difficultyLevel: DifficultyLevel) : Node() {

    private val game = Pong(context, difficultyLevel)
    private val ball = Node()
    private val player1Paddle = Node()
    private val player2Paddle = Node()

    private val scoreboardNode = Node()

    private var oldPlayer1Score = 0
    private var oldPlayer2Score = 0

    var playerInput: Int = 0

    override fun onActivate() {
        super.onActivate()

        if (scene == null) throw IllegalStateException("Scene is null!")

        createBoard()
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)

        game.handlePlayer1Input(playerInput)
        game.update()
        renderGame()
        updateScoreboard()
    }

    private fun updateScoreboard() {
        if (scoreboardNode.renderable == null) return

        if (oldPlayer1Score != game.player1Score || oldPlayer2Score != game.player2Score) {
            val scoreboardView = (scoreboardNode.renderable as ViewRenderable).view
            val player1ScoreTextView = scoreboardView.player1Score
            val player2ScoreTextView = scoreboardView.player2Score
            player1ScoreTextView.text = game.player1Score.toString()
            player2ScoreTextView.text = game.player2Score.toString()
            oldPlayer1Score = game.player1Score
            oldPlayer2Score = game.player2Score
        }
    }

    private fun renderGame() {
        if (player1Paddle.renderable == null ||
            player2Paddle.renderable == null ||
            ball.renderable == null
        ) return

        ball.localPosition = Vector3(0.0f + game.ball.x, ball.localPosition.y, game.ball.y + 0.0f)
        player1Paddle.localPosition = Vector3(
            0.0f + game.player1Paddle.x + (game.player1Paddle.width / 2),
            player1Paddle.localPosition.y,
            player1Paddle.localPosition.z
        )
        player2Paddle.localPosition = Vector3(
            0.0f + game.player2Paddle.x + (game.player2Paddle.width / 2),
            player2Paddle.localPosition.y,
            player2Paddle.localPosition.z
        )
    }

    private fun createBoard() {
        createBall()
        createPaddles()
        createWalls()
        createScoreboard()
    }

    private fun createScoreboard() = CoroutineScope(Dispatchers.Main).launch {
        scoreboardNode.renderable = makeViewRenderable(context, R.layout.arpong_scoreboard)
        scoreboardNode.localPosition = Vector3(0.0f, 0.5f, 0.0f)
        addChild(scoreboardNode)
    }

    private fun createBall() = CoroutineScope(Dispatchers.Main).launch {
        ball.renderable = makeSphereRenderable(
            context,
            0.05f,
            Vector3(0.0f, 0.0f, 0.0f),
            Color(parseColor("#ad4a3e"))
        )
        addChild(ball)
    }

    private fun createPaddles() = CoroutineScope(Dispatchers.Main).launch {
        val paddleSize = Vector3(0.2f, 0.1f, 0.05f)
        val paddleColor = Color(parseColor("#379457"))

        player1Paddle.renderable = makeCubeRenderable(
            context,
            paddleSize,
            Vector3(0.0f, 0.0f, 0.5f),
            paddleColor
        )

        player2Paddle.renderable = makeCubeRenderable(
            context,
            paddleSize,
            Vector3(0.0f, 0.0f, -0.5f),
            paddleColor
        )

        addChild(player1Paddle)
        addChild(player2Paddle)
    }

    private fun createWalls() = CoroutineScope(Dispatchers.Main).launch {
        val wallSize = Vector3(0.1f, 0.1f, 1.0f)
        val wallColor = Color(parseColor("#039dfc"))

        val leftWall = Node()
        leftWall.renderable = makeCubeRenderable(
            context,
            wallSize,
            Vector3(-0.5f, 0.0f, 0.0f),
            wallColor
        )

        val rightWall = Node()
        rightWall.renderable = makeCubeRenderable(
            context,
            wallSize,
            Vector3(0.5f, 0.0f, 0.0f),
            wallColor
        )

        addChild(leftWall)
        addChild(rightWall)
    }
}