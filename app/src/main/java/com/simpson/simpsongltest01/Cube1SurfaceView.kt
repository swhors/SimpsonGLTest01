package com.simpson.simpsongltest01

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import java.util.logging.Logger

class Cube1SurfaceView(context: Context?) : GLSurfaceView(context) {
    private var myRender: Cube1Renderer

    init {
        // Create an OpenGL ES 3.0 context.
        setEGLContextClientVersion(3)
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        // Set the Renderer for drawing on the GLSurfaceView
        myRender = Cube1Renderer()
        setRenderer(myRender)
        // Render the view only when there is a change in the drawing data
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    companion object {
        const val TOUCH_SCALE_FACTOR = 0015f

    }

    private var mPreviousX = 0f
    private var mPreviousY = 0f

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // eturn super.onTouchEvent(event)
        Logger.getLogger("CubeSurfaceView.onTouchEvent").info("${e.action}, ${e.x}, ${e.y}")
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                val dx = e.x - mPreviousX
                //subtract, so the cube moves the same direction as your finger.
                //with plus it moves the opposite direction.
                myRender.setX(myRender.getX() - (dx * TOUCH_SCALE_FACTOR))

                val dy = e.y -mPreviousY
                myRender.setY(myRender.getY() - (dy * TOUCH_SCALE_FACTOR))

            }
        }
        mPreviousX = e.x
        mPreviousY = e.y
        return true
    }
}