package com.simpson.simpsongltest01.shape

enum class ShapeType(val value: Int) {
    None(0),
    Triangle(1),
    QuadV1(2),
    QuadV2(3),
    Circle(4),
    Cube(5),
    CubeN(6);

    companion object {
        fun intToType(intType: Int): ShapeType {
            return when(intType) {
                0 -> None
                1 -> Triangle
                2 -> QuadV1
                3 -> QuadV2
                4 -> Circle
                5 -> Cube
                6 -> CubeN
                else -> None
            }
        }
    }
}