package com.chuahamilton.arpong.fragments


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import com.chuahamilton.arpong.R
import com.chuahamilton.arpong.arpong.ArPongBaseNode
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment


class GameFragment : ArFragment() {

    private var gamePlaced = false

    private lateinit var arPongBaseNode: ArPongBaseNode

    private lateinit var gestureDetector: GestureDetector

    private var paddleInput = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {

            override fun onDown(event: MotionEvent): Boolean {
                return true
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (e1 != null && e2 != null) {
                    val difference = e1.rawX - e2.rawX
                    when {
                        difference < 0 -> paddleInput = -1
                        difference == 0.0f -> paddleInput = 0
                        difference > 0 -> paddleInput = 1
                    }
                }
                if (gamePlaced)
                    arPongBaseNode.playerInput = paddleInput
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        })

        arSceneView.scene.setOnTouchListener { hitTestResult, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP && gamePlaced) {
                paddleInput = 0
                arPongBaseNode.playerInput = 0
            }
            gestureDetector.onTouchEvent(motionEvent)
        }

        setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (!gamePlaced) {
                placeGame(hitResult.createAnchor())
            }
        }

        return view
    }

    private fun placeGame(anchor: Anchor)  {
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arSceneView.scene)
        arPongBaseNode = ArPongBaseNode(activity as Context)
        anchorNode.addChild(arPongBaseNode)
        gamePlaced = true
    }
}
