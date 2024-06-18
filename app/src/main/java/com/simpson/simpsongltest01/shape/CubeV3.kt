package com.simpson.simpsongltest01.shape
import android.opengl.GLES20
import android.util.Log
import com.simpson.simpsongltest01.lib.MyColor
import com.simpson.simpsongltest01.lib.ShapeOpenGLUtil
import com.simpson.simpsongltest01.lib.ShapeType


class CubeV3: ShapeBase(shapeType = ShapeType.Cube3, coordsPerVertex = 3, mulValue = 4, renderMode = 0, defaultSize = 0.4f) {
    private var mMVPMatrixHandle = 0
    private var mColorHandle = 0

    companion object {
        const val vertexPosIndex = 0
        const val verticesPerface = 6
        //this is the initial data, which will need to translated into the mVertices variable in the constructor.
        val colorCyan = MyColor.cyan()
        val colorBlue = MyColor.blue()
        val colorRed = MyColor.red()
        val colorGray = MyColor.gray()
        val colorGreen = MyColor.green()
        val colorYellow = MyColor.yellow()
    }

    init {
        //setup the shaders
        val linked = IntArray(1)
        GLES20.glGetProgramiv(super.getProgramHandle(), GLES20.GL_LINK_STATUS, linked, 0)

        if (linked[0] == 0) {
            Log.e("${this.javaClass.name}.init", "Error linking program.")
            Log.e("${this.javaClass.name}.init", GLES20.glGetProgramInfoLog(super.getProgramHandle()))
            GLES20.glDeleteProgram(super.getProgramHandle())
            throw Exception("Illegal")
        }
    }

    override fun draw(mvpMatrix: FloatArray) {
        // Use the program object
        GLES20.glUseProgram(super.getProgramHandle())

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(super.getProgramHandle(), "uMVPMatrix")
        ShapeOpenGLUtil.checkGlError("glGetUniformLocation")
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)
        ShapeOpenGLUtil.checkGlError("glUniformMatrix4fv")

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(super.getProgramHandle(), "vColor")
        ShapeOpenGLUtil.checkGlError("glGetUniformLocation")

        // Apply the projection and view transformation

        val vertexBuffer = super.getVertexBuffer()
        vertexBuffer.position(vertexPosIndex) //just in case. We did it already though.

        //add all the points to the space, so they can be correct by the transformations.
        //would need to do this even if there were no transformations actually.
        GLES20.glVertexAttribPointer(vertexPosIndex, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)
        GLES20.glEnableVertexAttribArray(vertexPosIndex)

        //Now we are ready to draw the cube finally.
        var startPos =0

        //draw front face
        GLES20.glUniform4fv(mColorHandle, 1, colorBlue, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        startPos += verticesPerface

        //draw back face
        GLES20.glUniform4fv(mColorHandle, 1, colorCyan, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        startPos += verticesPerface

        //draw left face
        GLES20.glUniform4fv(mColorHandle, 1, colorRed, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        startPos += verticesPerface

        //draw right face
        GLES20.glUniform4fv(mColorHandle, 1, colorGray, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        startPos += verticesPerface

        //draw top face
        GLES20.glUniform4fv(mColorHandle, 1, colorGreen, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        startPos += verticesPerface

        //draw bottom face
        GLES20.glUniform4fv(mColorHandle, 1, colorYellow, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, startPos, verticesPerface)
        //last face, so no need to increment.
    }
}