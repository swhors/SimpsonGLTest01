package com.simpson.simpsongltest01.lib

enum class ShapeType(val value: Int) {
    None(0),
    Triangle(1),
    QuadV1(2),
    QuadV2(3),
    Circle(4),
    Cube(5),
    Cube2(6),
    Cube3(7);

    companion object {
        fun intToType(intType: Int): ShapeType {
            return when(intType) {
                0 -> None
                1 -> Triangle
                2 -> QuadV1
                3 -> QuadV2
                4 -> Circle
                5 -> Cube
                6 -> Cube2
                7 -> Cube3
                else -> None
            }
        }
    }
}