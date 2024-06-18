package com.simpson.simpsongltest01.lib

import java.util.logging.Logger

class ShaderCodes {
    companion object{
        fun getVertexCode(shapeType: ShapeType): String {
            Logger.getLogger("ShaderCoders.getVertexCode").info("shapeType = $shapeType")
            return when(shapeType) {
                ShapeType.Triangle, ShapeType.Circle, ShapeType.QuadV1, ShapeType.QuadV2, ShapeType.Cube2, ShapeType.Cube -> {
                    "uniform mat4 uMVPMatrix;\n" +
                            "attribute vec4 vPosition;\n" +
                            "void main() {\n" +
                            "    gl_Position = vPosition * uMVPMatrix;\n" +
                            "}"
                }
                ShapeType.Cube3 -> {
                    "uniform mat4 uMVPMatrix; \n" +
                            "attribute vec4 vPosition;" +
                            "void main() \n" +
                            "{ \n" +
                            "    gl_Position = uMVPMatrix * vPosition; \n" +
                            "}\n"
                }
                else -> ""
            }
        }
        fun getFragmentCode(shapeType: ShapeType): String {
            Logger.getLogger("ShaderCoders.getFragmentCode").info("shapeType = $shapeType")
            return when(shapeType) {
                ShapeType.Triangle, ShapeType.Circle, ShapeType.QuadV1, ShapeType.QuadV2, ShapeType.Cube2, ShapeType.Cube ->
                    "precision mediump float;\n" +
                            "uniform vec4 vColor;\n" +
                            "void main() {\n" +
                            "    gl_FragColor = vColor;\n" +
                            "}"
                ShapeType.Cube3 ->
                    "precision mediump float;\n" +
                            "uniform vec4 vColor;\n" +
                            "void main() {" +
                            "    gl_FragColor = vColor;\n" +
                            "}"
                ShapeType.None -> ""
            }
        }
    }
}