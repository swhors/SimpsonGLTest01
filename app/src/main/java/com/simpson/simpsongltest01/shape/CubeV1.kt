package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import android.opengl.GLES20.glGetUniformLocation
import android.util.Log
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import com.simpson.simpsongltest01.lib.ShapeType
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class CubeV1: ShapeBase(shapeType = ShapeType.Cube, coordsPerVertex = 3, mulValue = 4, renderMode = 0, defaultSize = 0.4f) {
    /** Shader code for the vertex.  */

    private var mColorBuffer: FloatBuffer? = null
    private var mIndexBuffer: ByteBuffer? = null

    companion object {
        /** Vertex colors.  */
        private val COLORS = floatArrayOf(
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
        )
// 0,1,2,0,2,3)

        /** Order to draw vertices as triangles.  */
        private val INDICES = byteArrayOf(
//            0, 1, 3, 3, 1, 2,  // Front face.
//            0, 1, 4, 4, 5, 1,  // Bottom face.
//            1, 2, 5, 5, 6, 2,  // Right face.
//            2, 3, 6, 6, 7, 3,  // Top face.
//            3, 7, 4, 4, 3, 0,  // Left face.
//            4, 5, 7, 7, 6, 5,  // Rear face.
            0, 1, 2, 0, 2, 3,  // Front face.
//            0, 1, 3, 3, 1, 2,  // Front face.
            3, 4, 2, 2, 4, 7,  // Bottom face.
            7, 6, 2, 2, 1, 6,  // Right face.
            6, 1, 5, 5, 1, 0,  // Top face.
            0, 3, 4, 4, 0, 5,  // Left face.
            5, 4, 7, 5, 6, 7,  // Rear face.
        )

        private const val COORDS_PER_VERTEX = 3

        /** Number of values per colors in [COLORS].  */
        private const val VALUES_PER_COLOR = 4

        /** Vertex size in bytes.  */
        private const val VERTEX_STRIDE = COORDS_PER_VERTEX * 4

        /** Color size in bytes.  */
        private const val COLOR_STRIDE = VALUES_PER_COLOR * 4
    }

    init{
        mColorBuffer = ShapeOpenGLUtil.allocFloatBuffer(COLORS)
        mIndexBuffer = ByteBuffer.allocateDirect(INDICES.size).run {
            this.order(ByteOrder.nativeOrder())
            apply {
                this.put(INDICES)
                this.position(0)
            }
        }
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix The Model View Project matrix in which to draw this shape
     */
    override fun draw(mvpMatrix: FloatArray) {
        // Add program to OpenGL environment.
        Log.i("CubeV1.draw", "programHandle = ${this.getProgramHandle()}")
        GLES20.glUseProgram(super.getProgramHandle())

        GLES20.glEnableVertexAttribArray(super.getPositionHandle())

        // triangle vertex 속성을 vertexBuffer에 저장 되어 있는 vertex 좌표들로 정의 한다.
        GLES20.glVertexAttribPointer(super.getPositionHandle(),
            3,
            GLES20.GL_FLOAT,
            false,
            VERTEX_STRIDE,
            super.getVertexBuffer())

        // Prepare the cube color data.
        var mColorHandle = glGetUniformLocation(super.getProgramHandle(), "vColor")
        Log.i("CubeV1.draw", "mColorHandle = $mColorHandle")
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glVertexAttribPointer(
            mColorHandle, 4, GLES20.GL_FLOAT, false, COLOR_STRIDE, mColorBuffer
        )

        // Apply the projection and view transformation.
        val mMVPMatrixHandle = glGetUniformLocation(super.getProgramHandle(), "uMVPMatrix")
        Log.i("CubeV1.draw", "mMVPMatrixHandle = $mMVPMatrixHandle")
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the cube.
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES, INDICES.size, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer
        )

        // Disable vertex arrays.
        GLES20.glDisableVertexAttribArray(super.getPositionHandle())
        GLES20.glDisableVertexAttribArray(mColorHandle)
    }
}