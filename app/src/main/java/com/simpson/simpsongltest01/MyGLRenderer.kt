package com.simpson.simpsongltest01

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.simpson.simpsongltest01.shape.Circle
import com.simpson.simpsongltest01.shape.QuadV1
import com.simpson.simpsongltest01.shape.QuadV2
import com.simpson.simpsongltest01.shape.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.concurrent.Volatile

class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var mTriangle: Triangle
    private lateinit var mQuadV1: QuadV1
    private lateinit var mQuadV2: QuadV2
    private lateinit var mCircle: Circle

    private var mMVPMatrix = FloatArray(16)
    private var mProjectionMatrix = FloatArray(16)
    private var mViewMatrix = FloatArray(16)
    private var mRotationMatrix = FloatArray(16)

    private var shapeType: Int = 1

    @Volatile
    var mAngle: Float = 0f

    fun getAngle() = mAngle
    fun setAngle(angle: Float) = angle.also { mAngle = it }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mTriangle = Triangle()
        mQuadV1 = QuadV1()
        mQuadV2 = QuadV2()
        mCircle = Circle()
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0, width, height)

        //GLSurfaceView 너비와 높이 사이의 비율을 계산합니다.
        val ratio: Float = width.toFloat() / height.toFloat()

        //3차원 공간의 점을 2차원 화면에 보여 주기 위해 사용 되는 projectionMatrix를 정의
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 3.0f, 7.0f)
    }

    fun changeView(viewType: Int) {
        this.shapeType = viewType
    }


    override fun onDrawFrame(gl: GL10?) {
        //glClearColor에서 설정한 값으로 color buffer를 클리어합니다.
        //glClear메소드를 사용하여 클리어할 수 있는 버퍼는 다음 3가지 입니다.
        //Color buffer (GL_COLOR_BUFFER_BIT)
        //depth buffer (GL_DEPTH_BUFFER_BIT)
        //stencil buffer (GL_STENCIL_BUFFER_BIT)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        //카메라 위치를 나타내는 Camera view matrix를 정의
        Matrix.setLookAtM(mViewMatrix, 0, 0.0f, 0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)
        // projection matrix와 camera view matrix를 곱하여 mMVPMatrix 변수에 저장
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)

        //mAngle 각도값을 이용하여 (x,y,z)=(0,0,-1) 축 주위를 회전하는 회전 matrix를 정의합니다.
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0.0f, 0.0f, -1.0f)

        //projection matrix와 camera view matrix를 곱하여 얻은 matrix인 mMVPMatrix와
        //회전 matrix mRotationMatrix를 결합합니다.
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0)

        //triangle를 그리는 처리를 하고 있는 draw메소드에 scratch 변수를 넘겨준다.
        when(this.shapeType) {
            1 -> mTriangle.draw(mMVPMatrix)
            2 -> mQuadV1.draw(mMVPMatrix)
            3 -> mCircle.draw(mMVPMatrix)
            4 -> mQuadV2.draw(mMVPMatrix)
        }
    }

    fun resetAllShapePosition() {

    }
}
