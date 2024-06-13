package com.simpson.simpsongltest01.lib

import android.opengl.GLES20
import com.simpson.simpsongltest01.shape.ShapeBase
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.util.logging.Logger
import kotlin.math.cos
import kotlin.math.sin
import com.simpson.simpsongltest01.shape.ShapeType

class ShapeOpenGLUtil {
    companion object {
        fun setPosition(proramHandle: Int, vertexBuffer: FloatBuffer, vertexStride: Int, normalized: Boolean, coordsPerVertex: Int): Int {
            // program 객체로부터 vertex shader의'vPosition 멤버에 대한 핸들을 가져옴
            val mPositionHandle = GLES20.glGetAttribLocation(proramHandle, "vPosition")
            // triangle vertex 속성을 활성화 시켜야 렌더링 시 반영 되어서 그려짐
            GLES20.glEnableVertexAttribArray(mPositionHandle)

            Logger.getLogger("ShadeOpenGLUtil").info("mPositionHandle = $mPositionHandle")

            // triangle vertex 속성을 vertexBuffer에 저장 되어 있는 vertex 좌표들로 정의 한다.
            GLES20.glVertexAttribPointer(mPositionHandle,
                coordsPerVertex,
                GLES20.GL_FLOAT,
                normalized,
                vertexStride,
                vertexBuffer)
            return mPositionHandle
        }

        fun allocFloatBuffer(coords: FloatArray): FloatBuffer {
            val bb = ByteBuffer.allocateDirect(coords.size * 4)
            bb.order(ByteOrder.nativeOrder())
            val vertexBuffer = bb.asFloatBuffer()
            vertexBuffer.put(coords)
            vertexBuffer.position(0)
            return vertexBuffer
        }

        fun intArrayToBuffer(datas: IntArray): IntBuffer {
            return ByteBuffer.allocateDirect(datas.size * 4).let {
                it.order(ByteOrder.nativeOrder())
                it.asIntBuffer()
            }.apply {
                put(datas)
                position(0)
            }
        }
        fun getVertexBuffer(shapeType: ShapeType =ShapeType.None, coords: FloatArray?) : FloatBuffer? {
            Logger.getLogger("ShapeOpenUtl.getVertexBuffer").info("$shapeType, $coords")
            if (coords == null) {
                val coordsLocal = if(shapeType != ShapeType.None) getCoords(shapeType) else null
                return coordsLocal?.let { allocFloatBuffer(it) }
            } else {
                return allocFloatBuffer(coords)
            }
        }

        private fun getCoords(shapeType: ShapeType): FloatArray? {
            return when(shapeType) {
                ShapeType.Triangle -> {
                    floatArrayOf(
                        0.0f, 0.6220085f, 0.0f,  // 0 top
                        -0.5f, -0.3110042f, 0.0f, // 1 right bottom
                        0.5f, -0.3110042f, 0.0f) // 2 left bottom
                }
                ShapeType.QuadV1 -> {
                    floatArrayOf(
                        -0.5f, 0.5f, // 0 left top
                        -0.5f, -0.5f, // 1 right top
                        0.5f, -0.5f, // right bottom
                        0.5f, 0.5f) // left bottom
                }
                ShapeType.QuadV2 -> {
                    floatArrayOf(
                        -0.5f, 0.5f, // 0
                        -0.5f, -0.5f, // 1
                        0.5f, -0.5f, // 2
                        -0.5f, 0.5f, // 0
                        0.5f, -0.5f, // 2
                        0.5f, 0.5f) // 3
                }
                ShapeType.Cube -> {
                    floatArrayOf(
                        -1.0f,  1.0f, -1.0f, /* Back. */
                        1.0f,  1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f,  1.0f,  1.0f, /* Front. */
                        1.0f,  1.0f,  1.0f,
                        -1.0f, -1.0f,  1.0f,
                        1.0f, -1.0f,  1.0f,
                        -1.0f,  1.0f, -1.0f, /* Left. */
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f,  1.0f,
                        -1.0f,  1.0f,  1.0f,
                        1.0f,  1.0f, -1.0f, /* Right. */
                        1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f,  1.0f,
                        1.0f,  1.0f,  1.0f,
                        -1.0f, -1.0f, -1.0f, /* Top. */
                        -1.0f, -1.0f,  1.0f,
                        1.0f, -1.0f,  1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f,  1.0f, -1.0f, /* Bottom. */
                        -1.0f,  1.0f,  1.0f,
                        1.0f,  1.0f,  1.0f,
                        1.0f,  1.0f, -1.0f)  // 7
                }
                ShapeType.CubeN -> {
                    floatArrayOf( // Vertices of the 6 faces
                        // FRONT
                        -0.5f, 0.5f, 0.0f,  // 0. left-bottom-front
                        -0.5f, -0.5f, 0.0f,  // 1. right-bottom-front
                        0.5f, -0.5f, 0.0f,  // 2. left-top-front
                        0.5f, 0.5f, 0.0f,  // 3. right-top-front
//                        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
//                        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
//                        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
//                        1.0f, 1.0f, 1.0f,  // 3. right-top-front
                        // BACK
                        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
                        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
                        1.0f, 1.0f, -1.0f,  // 7. right-top-back
                        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
                        // LEFT
                        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
                        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
                        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
                        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
                        // RIGHT
                        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
                        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
                        1.0f, 1.0f, 1.0f,  // 3. right-top-front
                        1.0f, 1.0f, -1.0f,  // 7. right-top-back
                        // TOP
                        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
                        1.0f, 1.0f, 1.0f,  // 3. right-top-front
                        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
                        1.0f, 1.0f, -1.0f,  // 7. right-top-back
                        // BOTTOM
                        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
                        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
                        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
                        1.0f, -1.0f, 1.0f // 1. right-bottom-front
                    )
                }
                ShapeType.Circle -> {
                    val coords = FloatArray(364 * 3)
                    coords[0] = 0f
                    coords[1] = 0f
                    coords[2] = 0f

                    for (i in 1..363) {
                        coords[i * 3 + 0] = (0.5 * cos((3.14 / 180) * i.toFloat())).toFloat()
                        coords[i * 3 + 1] = (0.5 * sin((3.14 / 180) * i.toFloat())).toFloat()
                        coords[i * 3 + 2] = 0f
                    }
                    coords
                }
                else -> { null }
            }
        }

        fun initProgram(vertexShaderCode: String?, fragmentShaderCode: String?): Int {
            val vertexShader =
                vertexShaderCode?.let { loadShader(GLES20.GL_VERTEX_SHADER, it) }
            val fragmentShader = fragmentShaderCode?.let { loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode) }

            val mProgram = GLES20.glCreateProgram()
            vertexShader?.let { GLES20.glAttachShader(mProgram, vertexShader) }
            fragmentShader?.let { GLES20.glAttachShader(mProgram, fragmentShader) }
            GLES20.glLinkProgram(mProgram)
            return mProgram
        }

        private fun loadShader(type: Int, shaderCode: String): Int{
            return GLES20.glCreateShader(type).also {
                GLES20.glShaderSource(it, shaderCode)
                GLES20.glCompileShader(it)
            }
        }

        fun checkGlError(glOperation: String) {
            val error: Int = GLES20.glGetError()

            if (error != GLES20.GL_NO_ERROR) {
                Logger.getLogger("MyGLRenderer").info("$glOperation glError=$error")
                throw RuntimeException("$glOperation : glError = $error")
            }
        }
    }
}