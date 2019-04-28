package com.ciii.bobmu.ogllearn.airhockey_mallet;

import android.app.ActivityManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ciii.bobmu.ogllearn.R;
import com.ciii.bobmu.ogllearn.utils.LogUtil;

public class AirHockeyActivity extends AppCompatActivity {

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
        LogUtil.i("TAG", "AirHockeyActivity.onCreate: reqGlEsVersion=" + manager.getDeviceConfigurationInfo().reqGlEsVersion );
        if (supportEs2) {
            surfaceView = new GLSurfaceView(this);
            surfaceView.setEGLContextClientVersion(2);  //设置版本号，解决glCreateShader() return 0;
            render = new AirHockeyRender(this);
            surfaceView.setRenderer(render);
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
}
