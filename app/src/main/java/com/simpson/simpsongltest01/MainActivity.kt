package com.simpson.simpsongltest01

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.activity.ComponentActivity
import androidx.core.view.get


class MainActivity : ComponentActivity() {
    private lateinit var glView: MyGLSurfaceView
    private var currentGlView = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.glView = MyGLSurfaceView(this)
        setContentView(R.layout.main)

        findViewById<Button>(R.id.changeBtn).setOnClickListener{
            if(++currentGlView == 5)
                currentGlView = 1
            changeGLView(currentGlView)
        }

        findViewById<Button>(R.id.okBtn).setOnClickListener{
            finishAndRemoveTask()
        }

        findViewById<Button>(R.id.resetBtn).setOnClickListener{
            glView.resetAllShapePosition()
        }


        val layout1 = findViewById<RelativeLayout>(R.id.layout1)
        layout1.addView(this.glView)
        changeGLView(currentGlView)
    }

    private fun changeGLView(viewType: Int) {
        this.glView.changeShape(viewType)
//        (findViewById<RelativeLayout>(R.id.layout1)[1] as MyGLSurfaceView).changeShape(viewType)
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