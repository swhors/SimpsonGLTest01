package com.simpson.simpsongltest01

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var glView: GLSurfaceView
    private var currentGlView = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.glView = MyGLSurfaceView(this)
        setContentView(R.layout.main)
        val changeBtn = findViewById<Button>(R.id.changeBtn)
        changeBtn.setOnClickListener{ changeGLView(if(currentGlView==0){1}else{0})}
        val okBtn = findViewById<Button>(R.id.okBtn)
        okBtn.setOnClickListener{
            finishAndRemoveTask()
        }
        changeGLView(currentGlView)
    }

    private fun changeGLView(viewType: Int) {
        when(viewType) {
            0 -> {
                val layout1: RelativeLayout = findViewById(R.id.layout1)
                layout1.removeView(this.glView)
                layout1.addView(this.glView)
                currentGlView = 0
            }
            1 -> {
                val layout1: RelativeLayout = findViewById(R.id.layout1)
                layout1.removeView(this.glView)
                layout1.addView(this.glView)
                currentGlView = 1
            }
        }
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