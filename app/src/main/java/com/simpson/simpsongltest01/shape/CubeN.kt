package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.logging.Logger

class CubeN: ShapeBase(shapeType = ShapeType.CubeN, coordsPerVertex = 3, mulValue = 4, renderMode = GLES20.GL_TRIANGLES) {
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
    private val order = byteArrayOf(
        0, 2, 1, 0, 3, 2,
        1, 2, 6, 6, 5, 1,
        4, 5, 6, 6, 7, 4,
        2, 3, 6, 6, 3, 7,
        0, 7, 3, 0, 4, 7,
        0, 1, 5, 0, 5, 4)
//         0,  1,  2,  0,  2,  3,
//         0,  1,  2,  0,  2,  3,
//         0,  1,  2,  0,  2,  3,
//         0,  1,  2,  0,  2,  3,
//         0,  1,  2,  0,  2,  3,
//         0,  1,  2,  0,  2,  3)

//         0,  1,  2,  2,  1,  3,
//         4,  5,  6,  6,  5,  7,
//         8,  9, 10, 10,  9, 11,
//        12, 13, 14, 14, 13, 15,
//        16, 17, 18, 18, 17, 19,
//        20, 21, 22, 22, 21, 23)
    private val orderByte: ByteBuffer = ByteBuffer.allocateDirect(order.size).order(ByteOrder.nativeOrder())
        .apply {
            put(order)
            position(0)
        }

    private var orderStepByColor: Int = 0

    init {
        super.initProgram(vertexShaderCode, fragmentShaderCode)
        super.mColors = arrayOf( // Colors of the 6 faces
            floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f),  // 0. orange
            floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f),  // 1. violet
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),  // 2. green
            floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f),  // 3. blue
            floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f),  // 4. red
            floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f) // 5. yellow
        )
        orderStepByColor = orderByte.capacity() / mColors.size
    }
    override fun customInitProgram(mProgram: Int) {
    }
    override fun drawCustom(vertexCount: Int, cnt: Int) {
        Logger.getLogger("Cube").info("$vertexCount, $cnt, ${cnt*orderStepByColor}")
        orderByte.position(cnt * orderStepByColor)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, orderStepByColor, GLES20.GL_UNSIGNED_BYTE, orderByte)
    }
}