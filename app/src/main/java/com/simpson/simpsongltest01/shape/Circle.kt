package com.simpson.simpsongltest01.shape

import android.opengl.GLES20


class Circle: ShapeBase(shapeType = ShapeType.Circle, coordsPerVertex = 3, mulValue = 4, renderMode = GLES20.GL_TRIANGLE_FAN) {
    private val vertexShaderCode = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"

    init {
        super.initProgram(vertexShaderCode, fragmentShaderCode)
        super.color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    }

    override fun drawCustom(vertexCount: Int) {
        // Draw the triangle
        // vertexCount Muse be 364
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)
    }
}