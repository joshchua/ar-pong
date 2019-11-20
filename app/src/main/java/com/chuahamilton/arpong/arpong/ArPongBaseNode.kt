package com.chuahamilton.arpong.arpong

import android.content.Context
import android.graphics.Color.parseColor
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArPongBaseNode(private val context: Context) : Node() {

    private val ball = Node()

    private val player1Paddle = Node()

    private val player2Paddle = Node()

    var playerInput: Int = 0

    override fun onActivate() {
        super.onActivate()

        if (scene == null) throw IllegalStateException("Scene is null!")

        createBoard()
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        // TODO: Implement game loop
        if (player1Paddle.renderable == null) return

        if (playerInput < 0)
            player1Paddle.localPosition = Vector3(player1Paddle.localPosition.x + 0.01f, player1Paddle.localPosition.y, player1Paddle.localPosition.z)
        else if (playerInput > 0)
            player1Paddle.localPosition = Vector3(player1Paddle.localPosition.x - 0.01f, player1Paddle.localPosition.y, player1Paddle.localPosition.z)
    }

    private fun createBoard() {
        createBall()
        createPaddles()
        createWalls()
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
        val paddleSize = Vector3(0.2f, 0.1f, 0.1f)
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
            Vector3(-0.5f, 0.1f, 0.0f),
            wallColor
        )

        val rightWall = Node()
        rightWall.renderable = makeCubeRenderable(
            context,
            wallSize,
            Vector3(0.5f, 0.1f, 0.0f),
            wallColor
        )

        addChild(leftWall)
        addChild(rightWall)
    }
}