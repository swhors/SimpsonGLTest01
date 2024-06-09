package com.simpson.simpsongltest01

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

class MyGLSurfaceView(context: Context?) : GLSurfaceView(context) {
    lateinit var renderer : MyGLRenderer

    private val TOUCH_SCALE_FACTOR = 180.0f / 320
    private var mPreviousX = 0.0f
    private var mPreviousY = 0.0f

    init {
        setEGLContextClientVersion(2)
        renderer = MyGLRenderer()
        setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x = e.getX()
        val y = e.getY()
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                var dx = x - mPreviousX
                var dy = y - mPreviousY
                dx = if (y > height / 2) {dx * -1} else dx
                dy = if (x < width / 2) {dy * -1} else dy
                renderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR // 180.0f / 320
                requestRender()
            }
        }
        mPreviousX = x
        mPreviousY = y
        return true
    }

    fun changeView(viewType: Int) {
        this.renderer.changeView(viewType)
    }
}