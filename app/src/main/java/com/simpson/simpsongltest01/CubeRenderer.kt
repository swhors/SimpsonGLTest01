package com.simpson.simpsongltest01

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import com.simpson.simpsongltest01.shape.Circle
import com.simpson.simpsongltest01.shape.Cube
import com.simpson.simpsongltest01.shape.CubeN
import com.simpson.simpsongltest01.shape.QuadV1
import com.simpson.simpsongltest01.shape.QuadV2
import com.simpson.simpsongltest01.shape.ShapeType
import com.simpson.simpsongltest01.shape.Triangle
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class CubeRenderer : GLSurfaceView.Renderer {
    glView.changeShape(this.currentGlView)

}
