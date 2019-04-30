package com.ciii.bobmu.ogllearn.airhockey;

import android.app.ActivityManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.ciii.bobmu.ogllearn.R;
import com.ciii.bobmu.ogllearn.utils.LogUtil;

public class AirHockeyActivity extends AppCompatActivity
        implements View.OnTouchListener {

    private GLSurfaceView surfaceView;
    private AirHockeyRender render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final boolean supportEs2 = manager.getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
        LogUtil.i("TAG", "AirHockeyActivity.onCreate: reqGlEsVersion=" + manager.getDeviceConfigurationInfo().reqGlEsVersion);
        if (supportEs2) {
            surfaceView = new GLSurfaceView(this);
            surfaceView.setEGLContextClientVersion(2);  //设置版本号，解决glCreateShader() return 0;
            render = new AirHockeyRender(this);
            surfaceView.setRenderer(render);
            surfaceView.setOnTouchListener(this);
            setContentView(surfaceView);
        } else {
            render = null;
            setContentView(R.layout.activity_air_hockey);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (render != null) {
            surfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (render != null) {
            surfaceView.onPause();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event != null) {
            final float normalizedX = (event.getX() / (float) v.getWidth()) * 2 - 1;
            final float normalizedY=-((event.getY()/(float)v.getHeight())*2-1);

            LogUtil.i("TouchTAG", "AirHockeyActivity.onTouch: event =("+event.getX()+", "+event.getY()+")");
            LogUtil.i("TouchTAG", "AirHockeyActivity.onTouch: view size=("+v.getWidth()+", "+v.getHeight()+")");
            LogUtil.i("TouchTAG", "AirHockeyActivity.onTouch: normalizedX="+normalizedX+", normalizedY="+normalizedY);
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                surfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        render.handleTouchPress(normalizedX, normalizedY);
                    }
                });
            }else if(event.getAction()==MotionEvent.ACTION_MOVE){
                surfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        render.handleTouchDrag(normalizedX, normalizedY);
                    }
                });
            }
            return true;
        }
        return false;
    }
}
