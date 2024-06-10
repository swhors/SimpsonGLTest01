package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import java.nio.FloatBuffer
import java.util.logging.Logger

abstract class ShapeBase(shapeType: ShapeType, coordsPerVertex: Int, mulValue: Int, renderMode: Int) {
    private var mProgram: Int = 0
    private var shapeType: ShapeType = ShapeType.None
    private var vertexBuffer: FloatBuffer
    private var coordsPerVertex: Int = 0
    private var vertexCount = 0
    private var mPositionHandle = 0
    private var renderMode = 0

    private var vertexStride = 0 // COORDS_PER_VERTEX * 4

    lateinit var color: FloatArray

    enum class ShapeType(val value: Int) {
        None(0),
        Triangle(1),
        QuadV1(2),
        QuadV2(3),
        Circle(4),
        Cubic(5)
    }

    init {
        this.shapeType = shapeType
        this.coordsPerVertex = coordsPerVertex
        this.vertexStride = this.coordsPerVertex * mulValue
        this.vertexBuffer = ShapeOpenGLUtil.getVertexBuffer(this.shapeType, null)!!
        vertexCount = vertexBuffer.capacity() / coordsPerVertex
        Logger.getLogger("ShapeBase").info("$shapeType ${vertexBuffer.capacity()} $vertexCount $vertexStride")
        this.renderMode = renderMode
    }

    fun initProgram(vertexShaderCode: String?, fragmentShaderCode: String?) {
        this.mProgram = ShapeOpenGLUtil.initProgram(vertexShaderCode, fragmentShaderCode)
    }

    private fun getProgramHandle() = this.mProgram

    private fun drawFirst(color: FloatArray, mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)

        // set position
        mPositionHandle = ShapeOpenGLUtil.setPosition(
            mProgram,
            this.vertexBuffer,
            vertexStride,
            false,
            coordsPerVertex = coordsPerVertex
        )

        // 색상 설정
        ShapeOpenGLUtil.setColor(this.mProgram, color)

        // Apply the projection and view transformation
        val mMVPMatrixHandle = GLES20.glGetUniformLocation(getProgramHandle(), "uMVPMatrix")
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)
    }

    private fun drawEnd() {
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

    internal abstract fun drawCustom(vertexCount: Int)

    fun draw(mvpMatrix: FloatArray) {
        drawFirst(color = color, mvpMatrix = mvpMatrix)

        drawCustom(this.vertexCount)

        drawEnd()
    }

}