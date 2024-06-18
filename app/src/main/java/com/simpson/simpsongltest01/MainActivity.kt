package com.simpson.simpsongltest01

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var glView: MyGLSurfaceView
    private lateinit var cube1View: Cube1SurfaceView
    private lateinit var cube3View: Cube3SurfaceView
    private var currentGlView = 1
    private var currentViewMode = 0 // 0 = general shape, 1 = cube only

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.glView = MyGLSurfaceView(this)
        this.cube3View = Cube3SurfaceView(this)
        this.cube1View = Cube1SurfaceView(this)
        setContentView(R.layout.main)

        currentViewMode = 0

        findViewById<Button>(R.id.changeBtn).setOnClickListener{
            if (this.currentViewMode != 0) {
                this.currentViewMode = 0
                attacheShapeView(this.currentViewMode)
            } else {
                if (++currentGlView == 5) {
                    changeGLView(6)
                    currentGlView = 0
                } else {
                    changeGLView(currentGlView)
                }
                glView.requestRender()
            }
        }

        findViewById<Button>(R.id.okBtn).setOnClickListener{
            finishAndRemoveTask()
        }

        findViewById<Button>(R.id.resetBtn).setOnClickListener{

            glView.resetAllShapePosition()
        }

        findViewById<Button>(R.id.changeCube1ViewBtn).setOnClickListener {
            if (this.currentViewMode != 1) {
                this.currentViewMode = 1
                attacheShapeView(this.currentViewMode)
            }
        }

        findViewById<Button>(R.id.changeCube3ViewBtn).setOnClickListener {
            if (this.currentViewMode != 2) {
                this.currentViewMode = 2
                attacheShapeView(this.currentViewMode)
            }
        }

        findViewById<RelativeLayout>(R.id.layout1).let {
            it.addView(this.cube3View)
            it.addView(this.cube1View)
            it.addView(this.glView)
        }

        attacheShapeView(viewMode = this.currentViewMode)
        glView.changeShape(this.currentGlView)
    }

    private fun attacheShapeView(viewMode: Int) {
        when(viewMode) {
            0 -> {
                this.glView.visibility = View.VISIBLE
                this.cube1View.visibility = View.INVISIBLE
                this.cube3View.visibility = View.INVISIBLE
                glView.requestRender()
            }
            1 -> {
                this.glView.visibility = View.INVISIBLE
                this.cube1View.visibility = View.VISIBLE
                this.cube3View.visibility = View.INVISIBLE
                cube1View.requestRender()
            }
            2 -> {
                this.glView.visibility = View.INVISIBLE
                this.cube1View.visibility = View.INVISIBLE
                this.cube3View.visibility = View.VISIBLE
                cube3View.requestRender()
            }
        }
    }

    private fun changeGLView(viewType: Int) {
        this.glView.changeShape(viewType)
    }

    private fun detectOpenGLES30(): Boolean {
        val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info: ConfigurationInfo = manager.deviceConfigurationInfo
        return (info.reqGlEsVersion >= 0x30000)
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