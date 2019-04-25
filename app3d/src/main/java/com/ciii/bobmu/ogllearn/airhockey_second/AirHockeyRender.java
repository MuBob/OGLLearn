package com.ciii.bobmu.ogllearn.airhockey_second;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.widget.Toast;

import com.ciii.bobmu.ogllearn.R;
import com.ciii.bobmu.ogllearn.utils.LogUtil;
import com.ciii.bobmu.ogllearn.utils.MatrixHelper;
import com.ciii.bobmu.ogllearn.utils.ShaderHelper;
import com.ciii.bobmu.ogllearn.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINE_STRIP;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

class AirHockeyRender implements GLSurfaceView.Renderer {

//    private final static int POSITION_COMPONENT_COUNT = 4;
    private final static int POSITION_COMPONENT_COUNT = 2;
    private final static int COLOR_COMPONENT_COUNT = 4;
    private final static int BYTES_PER_FLOAT = 4;
    private final static int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private Context context;
    private final FloatBuffer vertexData;
    private int program;
    //    private static final String U_COLOR = "u_Color";
//    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String A_COLOR = "a_Color";
    private int aColorLocation;
    private static final String U_MATRIX="u_Matrix";
    private int uMatrixLocation;
    private final float[] projectionMatrix=new float[16];  //顶点数组转换为矩阵存储
    private final float[] modelMatrix=new float[16];

    /**
     * 使用winding order两个三角形表示桌子的四个顶点
     */
    private float[] tableVertexWithTriangles = {
            //Triangle 1
//            0f, 0f,
//            9f, 14f,
//            0f, 14f,
//            -0.5f, -0.5f,
//            0.5f, 0.5f,
//            -0.5f, 0.5f,
//            Triangle 2
//            0f, 0f,
//            9f, 0f,
//            9f, 14f,
//            -0.5f, -0.5f,
//            0.5f, -0.5f,
//            0.5f, 0.5f,
//            Order of coordinates: X, Y, R, G, B, A
//            Triangle Fan
            0f, 0f, 1f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f, 1f,
            0.5f, -0.8f, 0.7f, 0.7f, 0.7f, 1f,
            0.5f, 0.8f, 0.7f, 0.7f, 0.7f, 1f,
            -0.5f, 0.8f, 0.7f, 0.7f, 0.7f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f, 1f,
            //Line middle
//            0f, 7f,
//            9f, 7f,
            -0.5f, 0f, 0f, 1f, 0f, 1f,
            0.5f, 0f, 0f, 1f, 0f, 1f,
//            Mallets
//            4.5f, 2f,
//            4.5f, 12f
            0f, -0.4f, 0f, 0f, 1f, 1f,
            0f, 0.4f, 1f, 0f, 0f, 1f
    };
/*
    private float[] tableVertexWithTriangles = {
//            Order of coordinates: X, Y, Z, W, R, G, B, A
//            Triangle Fan
            0f, 0f, 0f, 1.5f, 1f, 1f, 1f, 1f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f, 1f,
            0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f, 1f,
            0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f, 1f,
            -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f, 1f,
            -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f, 1f,
            //Line middle
            -0.5f, 0f, 0f, 1.5f, 0f, 1f, 0f, 1f,
            0.5f, 0f, 0f, 1.5f, 0f, 1f, 0f, 1f,
//            Mallets
            0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f, 1f,
            0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f, 1f
    };
    */
    public AirHockeyRender(Context context) {
        this.context = context;
        vertexData = ByteBuffer
                .allocateDirect(tableVertexWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertexWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 0f, 0f, 0f);
        String vertexShaderSource = TextResourceReader.readTextFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        if (vertexShader == 0 || fragmentShader == 0) {
            LogUtil.w("TAG", "AirHockeyRender.onSurfaceCreated: compile wrong!");
            Toast.makeText(context, "编译失败，无法正常加载", Toast.LENGTH_SHORT);
            return;
        }
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (!ShaderHelper.validateProgram(program)) {
            LogUtil.w("TAG", "AirHockeyRender.onSurfaceCreated: program is wrong!");
            return;
        }
        glUseProgram(program);
//        uColorLocation = glGetUniformLocation(program, U_COLOR);  //获取uniform位置
        aPositionLocation = glGetAttribLocation(program, A_POSITION);  //获取属性的uniform位置
        vertexData.position(0);  //禁止尝试读取缓冲区结尾后面的内容
        glVertexAttribPointer(
                //属性位置
                aPositionLocation,
                //属性关联的分量数量
                POSITION_COMPONENT_COUNT,
                //数据类型
                GL_FLOAT,
                //整型数据时有意义
                false,
                //一个数组存储多个属性时有意义
                STRIDE,
                //数据区
                vertexData
        );
        glEnableVertexAttribArray(aPositionLocation);  //允许使用顶点数组

        aColorLocation = glGetAttribLocation(program, A_COLOR);  //获取uniform位置
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(
                aColorLocation,
                COLOR_COMPONENT_COUNT,
                GL_FLOAT,
                false,
                STRIDE,
                vertexData
        );
        glEnableVertexAttribArray(aColorLocation);

        uMatrixLocation=glGetUniformLocation(program, U_MATRIX);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        /*
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
        */
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float)width/(float)height, 1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
        final  float[] temp=new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
//        绘制三角形桌子
//        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);  //更新着色器代码中的颜色值
//        glDrawArrays(GL_TRIANGLES, 0, 6);  //从顶点数组的0位置开始读顶点，一共读入6个长度
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);  //从顶点数组的0位置开始读顶点，一共读入6个长度
//        绘制分隔线
//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINE_STRIP, 6, 2);
//        绘制两个木槌
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);

        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
    }
}
