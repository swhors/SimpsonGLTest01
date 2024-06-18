package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeType
import java.nio.ByteBuffer
import java.nio.ByteOrder

class QuadV1: ShapeBase(shapeType = ShapeType.QuadV1, coordsPerVertex = 2, mulValue = 4, renderMode = GLES20.GL_TRIANGLE_FAN) {
    private val order = byteArrayOf(0,1,2,0,2,3)
    private val orderByte: ByteBuffer = ByteBuffer.allocateDirect(order.size).order(ByteOrder.nativeOrder())
        .apply {
        put(order)
        position(0)
    }

    private var orderStepByColor: Int = 0

    init {
        super.mColors = arrayOf(floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f))
        orderStepByColor = orderByte.capacity() / mColors.size
    }

    override fun drawCustom(colorHandle: Int, vertexCount: Int) {
        GLES20.glUniform4fv(colorHandle, 1, super.mColors[0], 0)
        orderByte.position(0)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, orderByte.capacity(), GLES20.GL_UNSIGNED_BYTE, orderByte)
    }
}