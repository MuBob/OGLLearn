package com.ciii.bobmu.ogllearn.airhockey;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private final static int BYTES_PER_FLOAT=4;
    private final FloatBuffer vertexData;

    /**
     * 使用winding order两个三角形表示桌子的四个顶点
     */
    private float[] tableVertexWithTriangles = {
            //Triangle 1
            0f, 0f,
            9f, 14f,
            0f, 14f,
            //Triangle 2
            0f, 0f,
            9f, 14f,
            9f, 0f,
            //Line middle
            0f, 4f,
            9f, 4f,
            //Mallets
            4.5f, 2f,
            4.5f, 12f
    };

    public AirHockeyRender() {
        vertexData= ByteBuffer
                .allocateDirect(tableVertexWithTriangles.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertexWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
