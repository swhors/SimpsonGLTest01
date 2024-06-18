package com.simpson.simpsongltest01.shape

import android.opengl.GLES20
import com.simpson.simpsongltest01.lib.ShapeType
import java.util.logging.Logger

class Triangle: ShapeBase(shapeType = ShapeType.Triangle, coordsPerVertex = 3, mulValue = 4, renderMode = GLES20.GL_TRIANGLES) {
    init {
        Logger.getLogger("Triangle.init").info("Triangle.init -- start")
        super.mColors = arrayOf(floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f))
        Logger.getLogger("Triangle.init").info("Triangle.init -- end")
    }

    override fun drawCustom(colorHandle: Int, vertexCount: Int) {
        GLES20.glUniform4fv(colorHandle, 1, super.getColorFirst()!!, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
    }
}