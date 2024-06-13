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


class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var mTriangle: Triangle
    private lateinit var mQuadV1: QuadV1
    private lateinit var mQuadV2: QuadV2
    private lateinit var mCircle: Circle
    private lateinit var mCubeN: CubeN
    private lateinit var mCube: Cube

    private var mMVPMatrix = FloatArray(16)
    private var mProjectionMatrix = FloatArray(16)
    private var mViewMatrix = FloatArray(16)
    private var mRotationMatrix = FloatArray(16)

    private var mShapeType: ShapeType = ShapeType.None
    fun setShapeType(value: ShapeType) = run{mShapeType = value}
    fun getShapeType() = mShapeType

    private var mCubeRotation = 0f
    private var mLastUpdateMillis: Long = 0

    var mAngle: Float = 0f
    fun getAngle() = mAngle
    fun setAngle(angle: Float) = run{mAngle = angle}

    private var mAngleX: Float = 0f
    fun setAngleX(angle: Float) = run{ mAngleX=angle}

    private var mAngleY: Float = 0f
    fun setAngleY(angle: Float) = run { mAngleY = angle}

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mTriangle = Triangle()
        mQuadV1 = QuadV1()
        mQuadV2 = QuadV2()
        mCircle = Circle()
        mCubeN = CubeN()
        mCube = Cube()

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0, width, height)

        //GLSurfaceView 너비와 높이 사이의 비율을 계산합니다.
        val ratio: Float = width.toFloat() / height.toFloat()

        //3차원 공간의 점을 2차원 화면에 보여 주기 위해 사용 되는 projectionMatrix를 정의
        Matrix.frustumM(mProjectionMatrix, 0,
            -ratio, ratio,
            -1.0f, 1.0f,
            3.0f, 7.0f)
    }

    fun changeView(viewType: ShapeType) {
        this.mShapeType = viewType
    }


    override fun onDrawFrame(gl: GL10?) {
        //glClearColor에서 설정한 값으로 color buffer를 클리어합니다.
        //glClear메소드를 사용하여 클리어할 수 있는 버퍼는 다음 3가지 입니다.
        //Color buffer (GL_COLOR_BUFFER_BIT)
        //depth buffer (GL_DEPTH_BUFFER_BIT)
        //stencil buffer (GL_STENCIL_BUFFER_BIT)
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        //카메라 위치를 나타내는 Camera view matrix를 정의
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -5f, 0f, 0f, -1f, 0f, 1f, 0f);

        // projection matrix와 camera view matrix를 곱하여 mMVPMatrix 변수에 저장
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)

        //mAngle 각도값을 이용하여 (x,y,z)=(0,0,-1) 축 주위를 회전하는 회전 matrix를 정의합니다.
//        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0.0f, 0.0f, -1.0f)
        Logger.getLogger("MyGlRender.onDrawFrame").info("mAngleX = $mAngleX, mAngleY = $mAngleY, mAngle=$mAngle")
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, mAngleX, mAngleY, -1.0f)
        Logger.getLogger("MyGlRender.onDrawFrame").info("mShapeType = $mShapeType")
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0)
        Logger.getLogger("MyGlRender.onDrawFrame").info("mMVPMatrix = $mMVPMatrix")
        when(this.mShapeType) {
            ShapeType.Triangle -> mTriangle.draw(mMVPMatrix)
            ShapeType.QuadV1 -> mQuadV1.draw(mMVPMatrix)
            ShapeType.Circle -> mCircle.draw(mMVPMatrix)
            ShapeType.QuadV2 -> mQuadV2.draw(mMVPMatrix)
            ShapeType.Cube -> {
                mCube.draw(mMVPMatrix)
                updateCubeRotation()
            }
            else -> {}
        }
    }

    private val CUBE_ROTATION_INCREMENT = 0.6f

    /** The refresh rate, in frames per second.  */
    private val REFRESH_RATE_FPS = 60

    /** The duration, in milliseconds, of one frame.  */
    private val FRAME_TIME_MILLIS: Float = TimeUnit.SECONDS.toMillis(1).toFloat() / REFRESH_RATE_FPS.toFloat()

    private fun updateCubeRotation() {
        if (mLastUpdateMillis !== 0L) {
            val factor: Float =
                (SystemClock.elapsedRealtime() - mLastUpdateMillis) / FRAME_TIME_MILLIS
            mCubeRotation += CUBE_ROTATION_INCREMENT * factor
        }
        mLastUpdateMillis = SystemClock.elapsedRealtime()
    }

    fun resetAllShapePosition() {

    }
}
