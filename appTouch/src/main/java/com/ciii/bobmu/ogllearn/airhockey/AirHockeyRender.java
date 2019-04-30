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
import com.ciii.bobmu.ogllearn.utils.Geometry;
import com.ciii.bobmu.ogllearn.utils.LogUtil;
import com.ciii.bobmu.ogllearn.utils.MatrixHelper;
import com.ciii.bobmu.ogllearn.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private final float[] projectionMatrix = new float[16];  //顶点数组转换为矩阵存储
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];
    private Context context;
    private Table table;  //桌子
    private Mallet mallet;  //木槌
    private Puck puck;  //冰球
    private TextureShaderProgram textureProgram;  //纹理加载程序
    private ColorShaderProgram colorProgram;  //着色加载程序
    private int texture;
    private boolean malletPressed = false;  //跟踪木槌触摸状态
    private Geometry.Point myMalletPosition;  //存储木槌当前位置

    public AirHockeyRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);
        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.mipmap.bg);
        myMalletPosition = new Geometry.Point(0f, mallet.height / 2f, 0.4f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);//创建特殊类型矩阵，眼睛位于x-z平面上方1.2个单位，并向后2.2个单位；头指向(0,1,0)方向
//        Matrix.setLookAtM(viewMatrix, 0, 0f, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 1f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 1f, 0f);  //绿色
        mallet.bindData(colorProgram);
        mallet.draw();


//        positionObjectInScene(0f, mallet.height/2f, 0.4f);
        positionObjectInScene(myMalletPosition.x, myMalletPosition.y, myMalletPosition.z);
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);// 红色
        //这里不需要再次定义数据，只需要原数据更新位置后绘制即可
        mallet.draw();

        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);  //银色
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

    public void handleTouchPress(float normalizedX, float normalizedY) {
        Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
        Geometry.Sphere malletBoundingSphere = new Geometry.Sphere(
                new Geometry.Point(myMalletPosition.x, myMalletPosition.y, myMalletPosition.z),
                mallet.height / 2f
        );
        malletPressed = Geometry.intersects(malletBoundingSphere, ray);

        Geometry.Plane plane = new Geometry.Plane(new Geometry.Point(0, 0, 0), new Geometry.Vector(0, 1, 0));
        Geometry.Point touchedPoint = Geometry.intersectionPoint(ray, plane);

        LogUtil.i("TouchTestTAG", "AirHockeyRender.handleTouchPress: touchX="+normalizedX+", touchY="+normalizedY);
        LogUtil.i("TouchTestTAG", "AirHockeyRender.handleTouchPress: touchRay="+ray);
        LogUtil.i("TouchTestTAG", "AirHockeyRender.handleTouchPress: touchPoint="+touchedPoint);
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        if (malletPressed) {
            Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
            LogUtil.i("TouchTAG", "AirHockeyRender.handleTouchDrag: ray="+ray);
            Geometry.Plane plane = new Geometry.Plane(new Geometry.Point(0, 0, 0), new Geometry.Vector(0, 1, 0));
            LogUtil.i("TouchTAG", "AirHockeyRender.handleTouchDrag: plan="+plane);
            Geometry.Point touchedPoint = Geometry.intersectionPoint(ray, plane);
            LogUtil.i("TouchTAG", "AirHockeyRender.handleTouchDrag: touchPoint="+touchedPoint);
            myMalletPosition = new Geometry.Point(touchedPoint.x, mallet.height / 2f, touchedPoint.z);
            LogUtil.i("TouchTAG", "AirHockeyRender.handleTouchDrag: malletPoint="+myMalletPosition);
        }
    }

    private Geometry.Ray convertNormalized2DPointToRay(float normalizedX, float normalizedY) {
        final float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        final float[] farPointNdc = {normalizedX, normalizedY, 1, 1};
        LogUtil.i("ConvertTAG", "AirHockeyRender.convertNormalized2DPointToRay: x="+normalizedX+", y="+normalizedY);
        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];
        Matrix.multiplyMV(nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0);
        Matrix.multiplyMV(farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0);
        divideByW(nearPointWorld);
        divideByW(farPointWorld);
        Geometry.Point nearPointRay = new Geometry.Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Geometry.Point farPointRay = new Geometry.Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        LogUtil.i("ConvertTAG", "AirHockeyRender.convertNormalized2DPointToRay: near Point Ray="+nearPointRay);
        LogUtil.i("ConvertTAG", "AirHockeyRender.convertNormalized2DPointToRay: far Point Ray="+farPointRay);
        return new Geometry.Ray(nearPointRay, Geometry.vectorBetween(nearPointRay, farPointRay));
    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }
}
