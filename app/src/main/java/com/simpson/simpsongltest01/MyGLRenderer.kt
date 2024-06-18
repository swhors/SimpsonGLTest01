package com.simpson.simpsongltest01

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.simpson.simpsongltest01.shape.Circle
import com.simpson.simpsongltest01.shape.CubeV2
import com.simpson.simpsongltest01.shape.QuadV1
import com.simpson.simpsongltest01.shape.QuadV2
import com.simpson.simpsongltest01.lib.ShapeType
import com.simpson.simpsongltest01.shape.Triangle
import java.util.logging.Logger
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var mTriangle: Triangle
    private lateinit var mQuadV1: QuadV1
    private lateinit var mQuadV2: QuadV2
    private lateinit var mCircle: Circle
    private lateinit var mCubeV2: CubeV2

    private var mMVPMatrix = FloatArray(16)
    private var mProjectionMatrix = FloatArray(16)
    private var mViewMatrix = FloatArray(16)
    private var mRotationMatrix = FloatArray(16)

    private var mShapeType: ShapeType = ShapeType.None
    private var mAngleX: Float = 0f
    fun setAngleX(angle: Float) = run{ mAngleX=angle}

    private var mAngleY: Float = 0f
    fun setAngleY(angle: Float) = run { mAngleY = angle}

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mTriangle = Triangle()
        mQuadV1 = QuadV1()
        mQuadV2 = QuadV2()
        mCircle = Circle()
        mCubeV2 = CubeV2()

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        mWidth = width
        mHeight = height
        GLES20.glViewport(0, 0, width, height)

        //GLSurfaceView 너비와 높이 사이의 비율을 계산
        val ratio: Float = width.toFloat() / height.toFloat()

        if (this.mShapeType == ShapeType.Cube || this.mShapeType == ShapeType.Cube2) {
            Matrix.perspectiveM(
                mProjectionMatrix, 0, 53.13f, ratio,
                Cube3Renderer.Z_NEAR,
                Cube3Renderer.Z_FAR
            )
        } else {
            //3차원 공간의 점을 2차원 화면에 보여 주기 위해 사용 되는 projectionMatrix를 정의
            Matrix.frustumM(
                mProjectionMatrix, 0,
                -ratio, ratio,
                -1.0f, 1.0f,
                3.0f, 7.0f
            )
        }
    }

    fun changeView(viewType: ShapeType) {
        this.mShapeType = viewType
    }
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mTransY=0f
    private var mTransX=0f

    private fun onDrawFrameInternal(gl:GL10?) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        //카메라 위치를 나타내는 Camera view matrix를 정의
        Matrix.setLookAtM(mViewMatrix, 0,
            0f, 0f, -5f,
            0f, 0f, 0f,
            0f, 1f, 0f)
        Matrix.setIdentityM(mRotationMatrix, 0)

        if (mShapeType != ShapeType.Cube2 && mShapeType != ShapeType.Cube) {
            Matrix.translateM(mRotationMatrix, 0, mTransX, mTransY, 0f)
            Matrix.setRotateM(mRotationMatrix, 0, this.getAngle(), 0.0f, 0.0f, -1.0f)
        } else {
            Matrix.translateM(mRotationMatrix, 0, mTransX, mTransY, 0f)
            //mangle is how fast, x,y,z which directions it rotates.
            Matrix.rotateM(mRotationMatrix, 0, this.getAngle(), 1.0f, 1.0f, 1.0f)
        }
        // projection matrix와 camera view matrix를 곱하여 mMVPMatrix 변수에 저장
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0)
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0)
    }

    fun getAngle(): Float {
        return when(this.mShapeType) {
            ShapeType.Triangle -> mTriangle.getAngle()
            ShapeType.QuadV1 -> mQuadV1.getAngle()
            ShapeType.Circle -> mCircle.getAngle()
            ShapeType.QuadV2 -> mQuadV2.getAngle()
            ShapeType.Cube2 -> mCubeV2.getAngle()
            else -> 0f
        }
    }

    fun setAngle(angle: Float) {
        when(this.mShapeType) {
            ShapeType.Triangle -> mTriangle.setAngle(angle)
            ShapeType.QuadV1 -> mQuadV1.setAngle(angle)
            ShapeType.Circle -> mCircle.setAngle(angle)
            ShapeType.QuadV2 -> mQuadV2.setAngle(angle)
            ShapeType.Cube2 -> mCubeV2.setAngle(angle)
            else -> {}
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        Logger.getLogger("MyGLRender.onDrawFrame").info("0")
        this.onDrawFrameInternal(gl = gl)
        when(this.mShapeType) {
            ShapeType.Triangle -> mTriangle.draw(mMVPMatrix)
            ShapeType.QuadV1 -> mQuadV1.draw(mMVPMatrix)
            ShapeType.Circle -> mCircle.draw(mMVPMatrix)
            ShapeType.QuadV2 -> mQuadV2.draw(mMVPMatrix)
            ShapeType.Cube2 -> {
                mCubeV2.draw(mMVPMatrix)
            }
            else -> {}
        }
    }

    fun resetAllShapePosition() {
        /* Do Nothing */
    }
}
