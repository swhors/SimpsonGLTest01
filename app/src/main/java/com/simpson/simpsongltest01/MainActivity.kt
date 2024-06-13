package com.simpson.simpsongltest01

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.activity.ComponentActivity
import androidx.core.view.get


class MainActivity : ComponentActivity() {
    private lateinit var glView: MyGLSurfaceView
    private lateinit var cubeView: CubeSurfaceView
    private var currentGlView = 1
    private var currentViewMode = 0 // 0 = general shape, 1 = cube only

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.glView = MyGLSurfaceView(this)
        this.cubeView = CubeSurfaceView(this)
        setContentView(R.layout.main)

        currentViewMode = 0

        findViewById<Button>(R.id.changeBtn).setOnClickListener{
            if (this.currentViewMode == 1) {
                this.currentViewMode = 0
                attacheShapeView(this.currentViewMode)
            } else {
                if (++currentGlView == 6)
                    currentGlView = 1
                changeGLView(currentGlView)
                glView.requestRender()
            }
        }

        findViewById<Button>(R.id.okBtn).setOnClickListener{
            finishAndRemoveTask()
        }

        findViewById<Button>(R.id.resetBtn).setOnClickListener{

            glView.resetAllShapePosition()
        }

        findViewById<Button>(R.id.changeCubeViewBtn).setOnClickListener {
            if (this.currentViewMode == 0) {
                this.currentViewMode = 1
                attacheShapeView(this.currentViewMode)
            }
        }
        findViewById<RelativeLayout>(R.id.layout1).let {
            it.addView(this.cubeView)
            it.addView(this.glView)
        }

        attacheShapeView(viewMode = this.currentViewMode)
        glView.changeShape(this.currentGlView)
    }

    private fun attacheShapeView(viewMode: Int) {
            when(viewMode) {
                0 -> {
                    this.glView.visibility = View.VISIBLE
                    this.cubeView.visibility = View.INVISIBLE
                    glView.requestRender()
                }
                else -> {
                    this.cubeView.visibility = View.VISIBLE
                    this.glView.visibility = View.INVISIBLE
                    cubeView.requestRender()
                }
            }
    }

    private fun changeGLView(viewType: Int) {
        this.glView.changeShape(viewType)
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SimpsonGLTest01Theme {
//        Greeting("Android")
//    }
//}