package com.simpson.simpsongltest01

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.logging.Logger

class Triangle {
    private var mProgram: Int = 0
    private var vertexBuffer: FloatBuffer
    private val COORDS_PER_VERTEX = 3
    private val triangleCoords = floatArrayOf(0.0f, 0.6220085f, 0.0f, -0.5f, -0.3110042f, 0.0f, 0.5f, -0.3110042f, 0.0f)
    private val colors = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var mPositionHandle = 0
    private var mColorHandle = 0

    private val vertexCount = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride = COORDS_PER_VERTEX * 4

    private val vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "    gl_Position = vPosition * uMVPMatrix;" +
            "}"

    private val fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "    gl_FragColor = vColor;" +
            "}"

    private var mMVPMatrixHandle = 0

    init {
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)
        //vertex shader 타입의 객체를 생성 하여 vertexShaderCode에 저장된 소스코드를 로드한 후,
        //컴파일 합니다.
        val vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
    }

    fun draw(mvpMatrix: FloatArray) {
        //렌더링 상태(Rendering State)의 한 부분으로 program을 추가 한다.
        GLES20.glUseProgram(mProgram)

        // program 객체로부터 vertex shader의'vPosition 멤버에 대한 핸들을 가져옴
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        // triangle vertex 속성을 활성화 시켜야 렌더링 시 반영 되어서 그려짐
        GLES20.glEnableVertexAttribArray(mPositionHandle)

        Logger.getLogger("Triangle").info("mPositionHandle = $mPositionHandle")

        // triangle vertex 속성을 vertexBuffer에 저장 되어 있는 vertex 좌표들로 정의 한다.
        GLES20.glVertexAttribPointer(mPositionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer)

        // program 객체로 부터 fragment shader의 vColor 멤버에 대한 핸들을 가져옴
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        Logger.getLogger("Triangle").info("mColorHandle = ${mColorHandle}")

        //triangle 렌더링 시 사용할 색으로 color 변수에 정의한 값을 사용 한다.
        GLES20.glUniform4fv(mColorHandle, 1, colors, 0)
        //program 객체로부터 vertex shader 타입의 객체에 정의된 uMVPMatrix에 대한 핸들을 획득합니다.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        //projection matrix와 camera view matrix를 곱하여 얻어진 mMVPMatrix 변수의 값을
        //vertex shader 객체에 선언된 uMVPMatrix 멤버에게로 넘겨줍니다.
        Logger.getLogger("Triangle").info("handle - $mMVPMatrixHandle")
        MyGLRenderer.checkGlError("glGetUniformLocation")
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)
        MyGLRenderer.checkGlError("glUniformMatrix4fv")
        Logger.getLogger("Triangle").info("handle - draw 03")

        //vertex 갯수만큼 tiangle을 렌더링한다.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        // vertex 속성을 비활성화 한다.
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}