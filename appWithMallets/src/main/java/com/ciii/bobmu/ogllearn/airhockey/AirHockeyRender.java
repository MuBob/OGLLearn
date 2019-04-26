package com.ciii.bobmu.ogllearn.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.ciii.bobmu.ogllearn.R;
import com.ciii.bobmu.ogllearn.objects.Mallet;
import com.ciii.bobmu.ogllearn.objects.Puck;
import com.ciii.bobmu.ogllearn.objects.Table;
import com.ciii.bobmu.ogllearn.programs.ColorShaderProgram;
import com.ciii.bobmu.ogllearn.programs.TextureShaderProgram;
import com.ciii.bobmu.ogllearn.utils.MatrixHelper;
import com.ciii.bobmu.ogllearn.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private final float[] projectionMatrix=new float[16];  //顶点数组转换为矩阵存储
    private final float[] modelMatrix=new float[16];
    private final float[] viewMatrix=new float[16];
    private final float[] viewProjectionMatrix=new float[16];
    private final float[] modelViewProjectionMatrix=new float[16];
    private Context context;
    private Table table;
    private Mallet mallet;
    private Puck puck;
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int texture;



    public AirHockeyRender(Context context) {
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        table=new Table();
        mallet=new Mallet(0.08f, 0.15f, 32);
        puck=new Puck(0.06f, 0.02f, 32);
        textureProgram=new TextureShaderProgram(context);
        colorProgram=new ColorShaderProgram(context);
        texture= TextureHelper.loadTexture(context, R.mipmap.bg);
 }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        final float aspectRatio=width>height?
                (float)width/(float)height:
                (float)height/(float)width;
        if(width>height){
            //Landscape
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }else{
            //Portrait or square
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float)width/(float)height, 1f, 10f);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);//创建特殊类型矩阵
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        positionObjectInScene(0f, mallet.height/2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScene(0f, mallet.height/2f, 0.4f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        //这里不需要再次定义数据，只需要原数据更新位置后绘制即可
        mallet.draw();

        positionObjectInScene(0f, puck.height/2f, 0f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();

    }

    private void positionObjectInScene(float x, float y, float z) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(
                modelViewProjectionMatrix,
                0,
                viewProjectionMatrix,
                0,
                modelMatrix,
                0);

    }

    private void positionTableInScene() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(
                modelViewProjectionMatrix,
                0,
                viewProjectionMatrix,
                0,
                modelMatrix,
                0);

    }
}
