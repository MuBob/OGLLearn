package com.ciii.bobmu.ogllearn.sf

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FirstActivitySurfaceRender:GLSurfaceView.Renderer {
    val TAG:String="SurfaceRenderLive"
    override fun onDrawFrame(gl: GL10?) {
        Log.i(TAG, "onDrawFrame()")
        //每次绘制一帧都会调用
        glClear(GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.i(TAG, "onSurfaceChanged(gl=$gl, width=$width, height=$height)")
        //Surface尺寸变化调用
        glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.i(TAG, "onSurfaceCreated(gl=$gl, config=$config)")
//        创建SurfaceView时调用
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f)
    }
}