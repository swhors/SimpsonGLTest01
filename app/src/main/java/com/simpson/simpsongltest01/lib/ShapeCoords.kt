package com.simpson.simpsongltest01.lib

import kotlin.math.cos
import kotlin.math.sin

class ShapeCoords {
    companion object {
        fun getCoords(shapeType: ShapeType, defaultSize: Float = 0f): FloatArray {
            return when(shapeType) {
                ShapeType.Triangle ->
                    floatArrayOf(
                        0.0f, 0.6220085f, 0.0f,  // 0 top
                        -0.5f, -0.3110042f, 0.0f, // 1 right bottom
                        0.5f, -0.3110042f, 0.0f) // 2 left bottom
                ShapeType.QuadV1 ->
                    floatArrayOf(  // 0,1,2,0,2,3
                        -0.5f, 0.5f, // 0 left top
                        -0.5f, -0.5f, // 1 left bottom
                        0.5f, -0.5f, // 2 right bottom
                        0.5f, 0.5f) // 3 right top
                ShapeType.QuadV2 ->
                    floatArrayOf(
                        -0.5f, 0.5f, // 0
                        -0.5f, -0.5f, // 1
                        0.5f, -0.5f, // 2
                        -0.5f, 0.5f, // 0
                        0.5f, -0.5f, // 2
                        0.5f, 0.5f) // 3
                ShapeType.Cube2 ->
                    floatArrayOf(
                        -defaultSize,  defaultSize,  defaultSize, // 0
                         defaultSize,  defaultSize,  defaultSize, // 1
                         defaultSize, -defaultSize,  defaultSize, // 2
                        -defaultSize, -defaultSize,  defaultSize, // 3
                        -defaultSize,  defaultSize, -defaultSize, // 4
                         defaultSize,  defaultSize, -defaultSize, // 5
                         defaultSize, -defaultSize, -defaultSize, // 6
                        -defaultSize, -defaultSize, -defaultSize // 7
                    )
                ShapeType.Cube, ShapeType.Cube3 -> {
                    floatArrayOf(
                        ////////////////////////////////////////////////////////////////////
                        // FRONT
                        // //////////////////////////////////////////////////////////////////
                        // Triangle 1
                        -defaultSize, defaultSize, defaultSize, // top-left
                        -defaultSize, -defaultSize, defaultSize, // bottom-left
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        // Triangle 2
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        defaultSize, defaultSize, defaultSize, // top-right
                        -defaultSize, defaultSize, defaultSize, // top-left
                        ////////////////////////////////////////////////////////////////////
                        // BACK
                        ////////////////////////////////////////////////////////////////////
                        // Triangle 1
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        -defaultSize, -defaultSize, -defaultSize, // bottom-left
                        defaultSize, -defaultSize, -defaultSize, // bottom-right
                        // Triangle 2
                        defaultSize, -defaultSize, -defaultSize, // bottom-right
                        defaultSize, defaultSize, -defaultSize, // top-right
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        ////////////////////////////////////////////////////////////////////
                        // LEFT
                        ////////////////////////////////////////////////////////////////////
                        // Triangle 1
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        -defaultSize, -defaultSize, -defaultSize, // bottom-left
                        -defaultSize, -defaultSize, defaultSize, // bottom-right
                        // Triangle 2
                        -defaultSize, -defaultSize, defaultSize, // bottom-right
                        -defaultSize, defaultSize, defaultSize, // top-right
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        ////////////////////////////////////////////////////////////////////
                        // RIGHT
                        ////////////////////////////////////////////////////////////////////
                        // Triangle 1
                        defaultSize, defaultSize, -defaultSize, // top-left
                        defaultSize, -defaultSize, -defaultSize, // bottom-left
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        // Triangle 2
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        defaultSize, defaultSize, defaultSize, // top-right
                        defaultSize, defaultSize, -defaultSize, // top-left
                        ////////////////////////////////////////////////////////////////////
                        // TOP
                        ////////////////////////////////////////////////////////////////////
                        // Triangle 1
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        -defaultSize, defaultSize, defaultSize, // bottom-left
                        defaultSize, defaultSize, defaultSize, // bottom-right
                        // Triangle 2
                        defaultSize, defaultSize, defaultSize, // bottom-right
                        defaultSize, defaultSize, -defaultSize, // top-right
                        -defaultSize, defaultSize, -defaultSize, // top-left
                        ////////////////////////////////////////////////////////////////////
                        // BOTTOM
                        ////////////////////////////////////////////////////////////////////
                        // Triangle 1
                        -defaultSize, -defaultSize, -defaultSize, // top-left
                        -defaultSize, -defaultSize, defaultSize, // bottom-left
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        // Triangle 2
                        defaultSize, -defaultSize, defaultSize, // bottom-right
                        defaultSize, -defaultSize, -defaultSize, // top-right
                        -defaultSize, -defaultSize, -defaultSize // top-left
                    )
                }
                ShapeType.Circle -> {
                    val coords = FloatArray(364 * 3)
                    coords[0] = 0f
                    coords[1] = 0f
                    coords[2] = 0f

                    for (i in 1..363) {
                        coords[i * 3 + 0] = (0.5 * cos((3.14 / 180) * i.toFloat())).toFloat()
                        coords[i * 3 + 1] = (0.5 * sin((3.14 / 180) * i.toFloat())).toFloat()
                        coords[i * 3 + 2] = 0f
                    }
                    coords
                }
                else -> FloatArray(0)
            }
        }
    }
}