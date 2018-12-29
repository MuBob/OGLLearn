package com.ciii.bobmu.ogllearn

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ciii.bobmu.ogllearn.sf.FirstActivitySurfaceRender
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    val TAG="SurfaceRenderLive"
    internal var glSurfaceView: GLSurfaceView? = null
    internal var render: FirstActivitySurfaceRender? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        glSurfaceView = GLSurfaceView(this)
        val systemService = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val reqGlEsVersion = systemService.deviceConfigurationInfo.reqGlEsVersion
        tv_show_version.text = reqGlEsVersion.toString()
        val supportSV = reqGlEsVersion >= 0x20000
        Log.i(TAG, "support SurfaceView?=$supportSV")
        if (supportSV) {
            render = FirstActivitySurfaceRender()
            glSurfaceView!!.setRenderer(render)
            setContentView(glSurfaceView)
        } else {
            render = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (render != null)
            glSurfaceView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (render != null)
            glSurfaceView!!.onPause()
    }

}
