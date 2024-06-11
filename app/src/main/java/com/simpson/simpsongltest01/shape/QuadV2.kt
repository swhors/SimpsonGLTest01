package com.simpson.simpsongltest01.shape

import android.opengl.GLES20

class QuadV2: ShapeBase(shapeType = ShapeType.QuadV2, coordsPerVertex = 2, mulValue = 4, renderMode = GLES20.GL_TRIANGLE_FAN) {
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

    init {
        super.initProgram(vertexShaderCode, fragmentShaderCode)
        super.mColors = arrayOf(floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f))
    }

    override fun customInitProgram(mProgram: Int) {
    }

    override fun drawCustom(vertexCount: Int, cnt: Int) {
        //vertexCount = 6
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}