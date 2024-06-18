package com.simpson.simpsongltest01.lib

import android.opengl.GLES20
import android.util.Log
import com.simpson.simpsongltest01.Cube3Renderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.logging.Logger

class ShapeOpenGLUtil {
    companion object {
        fun allocFloatBuffer(coords: FloatArray): FloatBuffer {
            return ByteBuffer.allocateDirect(coords.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().also {
                it.put(coords)
                it.position(0)
            }
        }

        fun getVertexBuffer(shapeType: ShapeType = ShapeType.None, coords: FloatArray?, defaultSize: Float = 0f) : FloatBuffer? {
            Logger.getLogger("ShapeOpenUtl.getVertexBuffer").info("before $shapeType, $coords")
            if (coords == null) {
                val coordsLocal = if(shapeType != ShapeType.None) ShapeCoords.getCoords(shapeType, defaultSize=defaultSize) else null
                Logger.getLogger("ShapeOpenUtl.getVertexBuffer").info("after $shapeType, $coordsLocal")
                return coordsLocal?.let { allocFloatBuffer(it) }
            } else {
                return allocFloatBuffer(coords)
            }
        }

        fun createProgram(vertexShaderCode: String?, fragmentShaderCode: String?): Int {
            val vertexShader =
                vertexShaderCode?.let { loadShader(GLES20.GL_VERTEX_SHADER, it) }
            val fragmentShader = fragmentShaderCode?.let { loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode) }

            val programHandle = GLES20.glCreateProgram()
            if (programHandle == 0) {
                Log.e("ShapeOpenGLUtil.createProgram", "So some kind of error, but what?")
                throw Exception("Illegal")
            }
            vertexShader?.let { GLES20.glAttachShader(programHandle, vertexShader) }
            fragmentShader?.let { GLES20.glAttachShader(programHandle, fragmentShader) }
            // Bind vPosition to attribute 0
            return programHandle
        }

        fun loadShader(type: Int, shaderCode: String): Int{
            return GLES20.glCreateShader(type).also {
                val compiled = IntArray(1)
                GLES20.glShaderSource(it, shaderCode)
                GLES20.glCompileShader(it)

                // Check the compile status
                GLES20.glGetShaderiv(it, GLES20.GL_COMPILE_STATUS, compiled, 0)

                if (compiled[0] == 0) {
                    Log.e(Cube3Renderer.TAG, "Erorr!!!!")
                    Log.e(Cube3Renderer.TAG, GLES20.glGetShaderInfoLog(it))
                    GLES20.glDeleteShader(it)
                    return 0
                }
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