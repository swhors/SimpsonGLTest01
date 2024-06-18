package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeType

class QuadV2: ShapeBase(shapeType = ShapeType.QuadV2, coordsPerVertex = 2, mulValue = 4, renderMode = GLES20.GL_TRIANGLE_FAN) {
    init {
        super.mColors = arrayOf(floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f))
    }

    override fun drawCustom(vertexCount: Int, cnt: Int) {
        //vertexCount = 6
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}