package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import java.util.logging.Logger

class QuadV1: ShapeBase(shapeType = ShapeType.QuadV1, coordsPerVertex = 2, mulValue = 4, renderMode = GLES20.GL_TRIANGLE_FAN) {
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
    private val order = byteArrayOf(0,1,2,0,2,3)
    private val orderByte: ByteBuffer = ByteBuffer.allocateDirect(order.size).let {
        it.order(ByteOrder.nativeOrder())
    }.apply {
        put(order)
        position(0)
    }

    private var orderStepByColor: Int = 0

    init {
        super.initProgram(vertexShaderCode, fragmentShaderCode)
        super.mColors = arrayOf(floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f))
        orderStepByColor = orderByte.capacity() / mColors.size
    }

    override fun customInitProgram(mProgram: Int) {
    }

    override fun drawCustom(vertexCount: Int, cnt: Int) {
        orderByte.position(cnt * this.orderStepByColor)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, orderStepByColor, GLES20.GL_UNSIGNED_BYTE, orderByte)
    }
}