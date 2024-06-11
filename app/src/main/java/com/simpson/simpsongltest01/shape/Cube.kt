package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class Cube {

    /** Cube vertices  */
    private val VERTICES = floatArrayOf(
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f
    )

    /** Vertex colors.  */
    private val COLORS = floatArrayOf(
        0.0f, 1.0f, 1.0f, 1.0f,
        1.0f, 0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,
        1.0f, 0.0f, 1.0f, 1.0f,
        1.0f, 1.0f, 1.0f, 1.0f,
        0.0f, 1.0f, 1.0f, 1.0f,
    )


    /** Order to draw vertices as triangles.  */
    private val INDICES = byteArrayOf(
        0, 1, 3, 3, 1, 2,  // Front face.
        0, 1, 4, 4, 5, 1,  // Bottom face.
        1, 2, 5, 5, 6, 2,  // Right face.
        2, 3, 6, 6, 7, 3,  // Top face.
        3, 7, 4, 4, 3, 0,  // Left face.
        4, 5, 7, 7, 6, 5,  // Rear face.
    )

    /** Number of coordinates per vertex in [VERTICES].  */
    private val COORDS_PER_VERTEX = 3

    /** Number of values per colors in [COLORS].  */
    private val VALUES_PER_COLOR = 4

    /** Vertex size in bytes.  */
    private val VERTEX_STRIDE = COORDS_PER_VERTEX * 4

    /** Color size in bytes.  */
    private val COLOR_STRIDE = VALUES_PER_COLOR * 4

    /** Shader code for the vertex.  */
    private val VERTEX_SHADER_CODE = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "attribute vec4 vColor;" +
            "varying vec4 _vColor;" +
            "void main() {" +
            "  _vColor = vColor;" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    /** Shader code for the fragment.  */
    private val FRAGMENT_SHADER_CODE = "precision mediump float;" +
            "varying vec4 _vColor;" +
            "void main() {" +
            "  gl_FragColor = _vColor;" +
            "}"

    private var mVertexBuffer: FloatBuffer? = null
    private var mColorBuffer: FloatBuffer? = null
    private var mIndexBuffer: ByteBuffer? = null
    private var mProgram = 0
    private var mPositionHandle = 0
    private var mColorHandle = 0
    private var mMVPMatrixHandle = 0

    init{
        mVertexBuffer = ShapeOpenGLUtil.allocFloatBuffer(VERTICES)
        mColorBuffer = ShapeOpenGLUtil.allocFloatBuffer(COLORS)

        mIndexBuffer = ByteBuffer.allocateDirect(INDICES.size).order(ByteOrder.nativeOrder()).apply {
            put(INDICES)
            position(0)
        }

        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE))
        GLES20.glAttachShader(
            mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE)
        )
        GLES20.glLinkProgram(mProgram)

        mPositionHandle = glGetAttribLocation(mProgram, "vPosition")
        mColorHandle = glGetAttribLocation(mProgram, "vColor")
        mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix")
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix The Model View Project matrix in which to draw this shape
     */
    fun draw(mvpMatrix: FloatArray?) {
        // Add program to OpenGL environment.
        GLES20.glUseProgram(mProgram)

        // Prepare the cube coordinate data.
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(
            mPositionHandle, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer
        )

        // Prepare the cube color data.
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glVertexAttribPointer(
            mColorHandle, 4, GLES20.GL_FLOAT, false, COLOR_STRIDE, mColorBuffer
        )

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the cube.
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES, INDICES.size, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer
        )

        // Disable vertex arrays.
        GLES20.glDisableVertexAttribArray(mPositionHandle)
        GLES20.glDisableVertexAttribArray(mColorHandle)
    }

    /** Loads the provided shader in the program.  */
    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)

        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)

        return shader
    }
}