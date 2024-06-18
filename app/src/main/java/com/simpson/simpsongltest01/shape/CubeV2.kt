package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeType
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.logging.Logger

class CubeV2: ShapeBase(shapeType = ShapeType.Cube2, coordsPerVertex = 3, mulValue = 4, renderMode = GLES20.GL_TRIANGLES, defaultSize = 0.4f) {
    private val order = byteArrayOf(
        0, 1, 2, 0, 2, 3, // front
        3, 7, 6, 3, 6, 2, // bottom
        3, 0, 4, 3, 4, 7, // left
        7, 4, 5, 7, 5, 6, // rear
        6, 2, 1, 6, 1, 5, // right
        5, 1, 0, 5, 0, 4, // top
    )

    private val orderByte: ByteBuffer = ByteBuffer.allocateDirect(order.size).order(ByteOrder.nativeOrder())
        .apply {
            put(order)
            position(0)
        }

    private var orderStepByColor: Int = 0

    init {
        super.mColors = arrayOf( // Colors of the 6 faces
            floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f),  // 0. orange
            floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f),  // 1. violet
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),  // 2. green
            floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f),  // 3. blue
            floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f),  // 4. red
            floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f)   // 5. yellow
        )
        orderStepByColor = orderByte.capacity() / mColors.size
        Logger.getLogger("CubeV2.init").info("mProgram = ${super.getProgramHandle()}")
    }

    override fun drawCustom(colorHandle: Int, vertexCount: Int) {

        var cnt = 0
        // 색상 설정
        super.mColors.forEach {
            GLES20.glUniform4fv(colorHandle, 1, it, 0)
            Logger.getLogger("CubeV2.drawCustom").info("$vertexCount, $cnt, ${cnt*orderStepByColor}")
            orderByte.position(cnt * orderStepByColor)
            Logger.getLogger("CubeV2.drawCustom").info("$vertexCount, $cnt, $orderByte")
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, orderStepByColor, GLES20.GL_UNSIGNED_BYTE, orderByte)
            cnt++
        }
    }
}