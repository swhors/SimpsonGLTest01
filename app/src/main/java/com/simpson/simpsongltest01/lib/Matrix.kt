package com.simpson.simpsongltest01.lib

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Matrix {
     companion object {
         fun matrixIdentityFunction(matrix: FloatArray)
         {
             if(matrix.isEmpty())
             {
                 return;
             }
             matrix[0] = 1.0f;
             matrix[1] = 0.0f;
             matrix[2] = 0.0f;
             matrix[3] = 0.0f;
             matrix[4] = 0.0f;
             matrix[5] = 1.0f;
             matrix[6] = 0.0f;
             matrix[7] = 0.0f;
             matrix[8] = 0.0f;
             matrix[9] = 0.0f;
             matrix[10] = 1.0f;
             matrix[11] = 0.0f;
             matrix[12] = 0.0f;
             matrix[13] = 0.0f;
             matrix[14] = 0.0f;
             matrix[15] = 1.0f;
         }

         private fun matrixFrustum(matrix: FloatArray, left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float)
         {
             val temp = 2.0f * zNear
             val xDistance = right - left
             val yDistance = top - bottom
             val zDistance = zFar - zNear
             matrixIdentityFunction(matrix)
             matrix[0] = temp / xDistance
             matrix[5] = temp / yDistance
             matrix[8] = (right + left) / xDistance
             matrix[9] = (top + bottom) / yDistance
             matrix[10] = (-zFar - zNear) / zDistance
             matrix[11] = -1.0f
             matrix[14] = (-temp * zFar) / zDistance
             matrix[15] = 0.0f;
         }

         private fun matrixPerspective(matrix: FloatArray, fieldOfView: Float, aspectRatio: Float, zNear: Float, zFar: Float) {
             val ymax: Float = zNear * tan((fieldOfView * 3.14f) / 360f)
             val xmax: Float = ymax * aspectRatio
             matrixFrustum(matrix, -xmax, xmax, -ymax, ymax, zNear, zFar)
         }

         private fun matrixMultiply(destination: FloatArray, operand1: FloatArray, operand2: FloatArray)
         {
             val theResult = FloatArray(16)
             var row = 0
             var column = 0
             var i = 0
             var j = 0
             while (i < 4) {
                 j = 0
                 while(j < 4) {
                     theResult[4 * i + j] = operand1[j] * operand2[4 * i] + operand1[4 + j] * operand2[4 * i + 1] +
                             operand1[8 + j] * operand2[4 * i + 2] + operand1[12 + j] * operand2[4 * i + 3]
                     j++
                 }
                 i++
             }
             i = 0
             theResult.forEach { destination[i++] = it }
         }


         private fun matrixDegreesToRadians(degrees: Float): Float = (3.14f * degrees)/180.0f

         fun matrixRotateX(matrix: FloatArray, angle: Float) {
             val tempMatrix = FloatArray(16)
             matrixIdentityFunction(tempMatrix)
             tempMatrix[5] = cos(matrixDegreesToRadians(angle))
             tempMatrix[9] = -sin(matrixDegreesToRadians(angle))
             tempMatrix[6] = sin(matrixDegreesToRadians(angle))
             tempMatrix[10] = cos(matrixDegreesToRadians(angle))
             matrixMultiply(matrix, tempMatrix, matrix)
         }

         fun matrixRotateY(matrix: FloatArray, angle: Float) {
             val tempMatrix = FloatArray(16)
             matrixIdentityFunction(tempMatrix);
             tempMatrix[0] = cos(matrixDegreesToRadians(angle));
             tempMatrix[8] = sin(matrixDegreesToRadians(angle));
             tempMatrix[2] = -sin(matrixDegreesToRadians(angle));
             tempMatrix[10] = cos(matrixDegreesToRadians(angle));
             matrixMultiply(matrix, tempMatrix, matrix);
         }

         fun matrixRotateZ(matrix: FloatArray, angle: Float)
         {
             val tempMatrix = FloatArray(16)
             matrixIdentityFunction(tempMatrix)
             tempMatrix[0] = cos(matrixDegreesToRadians(angle))
             tempMatrix[4] = -sin(matrixDegreesToRadians(angle))
             tempMatrix[1] = sin(matrixDegreesToRadians(angle))
             tempMatrix[5] = cos(matrixDegreesToRadians(angle))
             matrixMultiply(matrix, tempMatrix, matrix)
         }

         fun matrixTranslate(matrix: FloatArray, x: Float, y: Float, z: Float)
         {
             val temporaryMatrix = FloatArray(16)
             matrixIdentityFunction(temporaryMatrix);
             temporaryMatrix[12] = x;
             temporaryMatrix[13] = y;
             temporaryMatrix[14] = z;
             matrixMultiply(matrix,temporaryMatrix,matrix);
         }
     }
}