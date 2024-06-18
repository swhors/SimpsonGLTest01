package com.simpson.simpsongltest01

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.simpson.simpsongltest01.lib.ShapeType
import java.util.logging.Logger
import kotlin.math.abs

class MyGLSurfaceView(context: Context?) : GLSurfaceView(context) {
    private var mRenderer : MyGLRenderer

    companion object{
        private const val TOUCH_SCALE_FACTOR = 180f / 320f
    }
    private var mPreviousX = 0.0f
    private var mPreviousY = 0.0f

    init {
        setEGLContextClientVersion(2)
        mRenderer = MyGLRenderer()
        setRenderer(mRenderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_UP -> {
                Logger.getLogger("MyGLSurfaceView.onTouchEvent").info("ACTION_UP, ${e.x}, ${e.y}")
                mPreviousX = 0f
                mPreviousY = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                Logger.getLogger("MyGLSurfaceView.onTouchEvent").info("ACTION_MOVE, ${e.x}, ${e.y}")
                if (mPreviousY != 0f || mPreviousY != 0f) {
                    var dx = abs(e.x - mPreviousX)
                    var dy = abs(e.y - mPreviousY)
                    dx = if (e.y > height / 2) {
                        dx * -1
                    } else dx
                    dy = if (e.x < width / 2) {
                        dy * -1
                    } else dy
                    val angle = mRenderer.getAngle() + (dx + dy) * TOUCH_SCALE_FACTOR
                    mRenderer.setAngle(angle = angle)
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
        Logger.getLogger("MyGLSurfaceView.setRender").info("setRenderer $renderer")
    }

    fun changeShape(shapeType: Int) {
        this.mRenderer.changeView(ShapeType.intToType(shapeType))
        requestRender()
    }

    fun resetAllShapePosition() {
        mRenderer.resetAllShapePosition()
    }
}