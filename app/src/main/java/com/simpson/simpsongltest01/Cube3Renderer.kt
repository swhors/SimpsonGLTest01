package com.simpson.simpsongltest01

import android.opengl.Matrix
import android.opengl.GLES20

import com.simpson.simpsongltest01.shape.CubeV3
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLSurfaceView
import java.util.logging.Logger


class Cube3Renderer : GLSurfaceView.Renderer {
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private lateinit var mCube: CubeV3

    private var mAngle =0f
    private var mTransY=0f
    private var mTransX=0f

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private val mMVPMatrix = FloatArray(16)
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mRotationMatrix = FloatArray(16)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig) {
        //set the clear buffer color to light gray.
        GLES20.glClearColor(0.9f, .9f, 0.9f, 0.9f)
        //set the clear buffer color to a dark grey.

        GLES20.glClearColor(0.1f, .1f, 0.1f, 0.9f)
        //initialize the cube code for drawing.
        mCube = CubeV3()
        //if we had other objects setup them up here as well.
    }

    override fun onSurfaceChanged(gl: GL10,width: Int, height: Int) {
        mWidth = width
        mHeight = height
        // Set the viewport
        GLES20.glViewport(0, 0, mWidth, mHeight)
        val aspect = width.toFloat() / height.toFloat()
        // this projection matrix is applied to object coordinates
        // no idea why 53.13f, it was used in another example and it worked.
        Matrix.perspectiveM(mProjectionMatrix, 0, 53.13f, aspect, Z_NEAR, Z_FAR)
    }

    override fun onDrawFrame(gl: GL10?) {
        // Clear the color bufferset above by glClearColor.
        Logger.getLogger("CubeRender.onDrawFrame").info("$gl")
        val option = GLES20.GL_COLOR_BUFFER_BIT.or(GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glClear(option)
        //need this otherwise, it will over right stuff and the cube will look wrong!
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        // Set the camera position (View matrix)     note Matrix is an include, not a declared method
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        // Create a rotation and translation for the cube
        Matrix.setIdentityM(mRotationMatrix, 0)
        //move the cube up/down and left/right
        Matrix.translateM(mRotationMatrix, 0, mTransX, mTransY, 0f)
        //mangle is how fast, x,y,z which directions it rotates.
        Matrix.rotateM(mRotationMatrix, 0, mAngle, 1.0f, 1.0f, 1.0f)
        // combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0)
        // combine the model-view with the projection matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0)
        mCube.draw(mMVPMatrix)
        //change the angle, so the cube will spin.
        mAngle+=4
    }

    //used the touch listener to move the cube up/down (y) and left/right (x)
    fun getY(): Float = mTransY
    fun setY(mY: Float) = run{ this.mTransY = mY}
    fun getX(): Float = mTransX
    fun setX(mX: Float) = run {mTransX = mX }

    companion object {
        const val TAG = "CubeRenderer"
        const val Z_NEAR = 1f
        const val Z_FAR = 40f
    }
}