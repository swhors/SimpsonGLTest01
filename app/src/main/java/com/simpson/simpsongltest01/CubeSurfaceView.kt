package com.simpson.simpsongltest01

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.simpson.simpsongltest01.shape.ShapeBase
import com.simpson.simpsongltest01.shape.ShapeType
import java.util.logging.Logger

class CubeSurfaceView(context: Context?) : GLSurfaceView(context) {
    var mRenderer : MyGLRenderer

    companion object{
        private const val TOUCH_SCALE_FACTOR = 180f / 320f
    }
    private var mPreviousX = 0.0f
    private var mPreviousY = 0.0f

    init {
        setEGLContextClientVersion(2)
        mRenderer = MyGLRenderer()
        setRenderer(mRenderer)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        Logger.getLogger("MyGLSurfaceView").info("${e.action}, ${e.x}, ${e.y}")
        when (e.action) {
            MotionEvent.ACTION_UP -> {
                mPreviousX = 0f
                mPreviousY = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                if (mPreviousY != 0f || mPreviousY != 0f) {
                    var dx = Math.abs(e.x - mPreviousX)
                    var dy = Math.abs(e.y - mPreviousY)
                    dx = if (e.y > height / 2) {
                        dx * -1
                    } else dx
                    dy = if (e.x < width / 2) {
                        dy * -1
                    } else dy
                    mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR // 180.0f / 320
                    requestRender()
                }
                mPreviousX = e.x
                mPreviousY = e.y
            }
        }
        return true
    }

    override fun setRenderer(renderer: Renderer?) {
        super.setRenderer(renderer)
        this.mRenderer = renderer as MyGLRenderer
        Logger.getLogger("MyGLSurfaceView").info("setRenderer $renderer")
    }

    fun changeShape(shapeType: Int) {
        this.mRenderer.changeView(ShapeType.intToType(shapeType))
        requestRender()
    }

    fun resetAllShapePosition() {
        mRenderer.resetAllShapePosition()
    }
}