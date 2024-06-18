package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShaderCodes
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import com.simpson.simpsongltest01.lib.ShapeType
import java.nio.FloatBuffer
import java.util.logging.Logger

abstract class ShapeBase(shapeType: ShapeType, coordsPerVertex: Int, mulValue: Int, renderMode: Int, defaultSize: Float = 0f) {
    private var mProgram: Int = 0
    private var mShapeType: ShapeType = ShapeType.None
    private var vertexBuffer: FloatBuffer

    fun getVertexBuffer() = vertexBuffer

    private var coordsPerVertex: Int = 0
    private var vertexCount = 0
    private var mPositionHandle = 0
    private var renderMode = 0

    private var fragmentCode: String
    private var vertexCode: String
    private var defaultSize = 0f

    private var vertexStride = 0 // COORDS_PER_VERTEX * 4

    private var mAngle = 0f
    fun getAngle() = mAngle
    fun setAngle(angle: Float) = run { this.mAngle = angle }

    var mColors: Array<FloatArray> = emptyArray()
    fun getColorFirst(): FloatArray? = (mColors.size>0).let { mColors[0] }

    init {
        Logger.getLogger("ShapeBase.init").info("ShapeBase.init -- start $shapeType")
        this.defaultSize = defaultSize
        this.mShapeType = shapeType
        this.coordsPerVertex = coordsPerVertex
        this.vertexStride = this.coordsPerVertex * mulValue
        this.vertexBuffer = ShapeOpenGLUtil.getVertexBuffer(this.mShapeType, null, defaultSize = defaultSize)!!
        vertexCount = vertexBuffer.capacity() / coordsPerVertex
        Logger.getLogger("ShapeBase.init").info("$mShapeType ${vertexBuffer.capacity()} $vertexCount $vertexStride")
        this.renderMode = renderMode
        this.vertexCode = ShaderCodes.getVertexCode(shapeType)
        this.fragmentCode = ShaderCodes.getFragmentCode(shapeType)
        Logger.getLogger("ShapeBase.init").info("vertexCode = ${this.vertexCode}")
        Logger.getLogger("ShapeBase.init").info("fragmentCode = ${this.fragmentCode}")
        this.mProgram = ShapeOpenGLUtil.createProgram(this.vertexCode, this.fragmentCode)
        Logger.getLogger("ShapeBase.init").info("mProgram = ${this.mProgram}")
        GLES20.glBindAttribLocation(this.mProgram, 0, "vPosition")
        ShapeOpenGLUtil.checkGlError("glBindAttribLocation")
        GLES20.glLinkProgram(this.mProgram)
        ShapeOpenGLUtil.checkGlError("glLinkProgram")
        this.mPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "vPosition")
        Logger.getLogger("ShapeBase.init").info("mPositionHandle = ${this.mPositionHandle}")
        ShapeOpenGLUtil.checkGlError("glGetAttribLocation")
        // triangle vertex 속성을 활성화 시켜야 렌더링 시 반영 되어서 그려짐

        Logger.getLogger("ShapeBase.init").info("ShapeBase.init -- end $shapeType")
    }

    fun getProgramHandle() = this.mProgram
    fun getPositionHandle() = this.mPositionHandle

    private fun drawFirst(mvpMatrix: FloatArray): Int {
        GLES20.glUseProgram(mProgram)

        GLES20.glEnableVertexAttribArray(mPositionHandle)

        // triangle vertex 속성을 vertexBuffer에 저장 되어 있는 vertex 좌표들로 정의 한다.
        GLES20.glVertexAttribPointer(mPositionHandle,
            coordsPerVertex,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer)

        // Apply the projection and view transformation
        val mMVPMatrixHandle = GLES20.glGetUniformLocation(getProgramHandle(), "uMVPMatrix")
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)

        return GLES20.glGetUniformLocation(getProgramHandle(), "vColor")
    }

    private fun drawEnd() {
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

    open fun drawCustom(colorHandle: Int, vertexCount: Int) {
        /* do nothing */
    }

    open fun draw(mvpMatrix: FloatArray) {
        Logger.getLogger("ShapeBase.draw").info("ShapeType = $mShapeType")
        val colorHandle = drawFirst(mvpMatrix = mvpMatrix)
        drawCustom(colorHandle = colorHandle, vertexCount = vertexCount)
        drawEnd()
    }
}