package com.ciii.bobmu.ogllearn.airhockey;

import android.app.ActivityManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ciii.bobmu.ogllearn.R;

public class AirHockeyActivity extends AppCompatActivity {

    private GLSurfaceView surfaceView;
    private AirHockeyRender render;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager manager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
        if(manager.getDeviceConfigurationInfo().reqGlEsVersion>0x20000){
            surfaceView=new GLSurfaceView(this);
            render=new AirHockeyRender();
            surfaceView.setRenderer(render);
            setContentView(surfaceView);
        }else {
            render=null;
            setContentView(R.layout.activity_air_hockey);
        }
    }
}
