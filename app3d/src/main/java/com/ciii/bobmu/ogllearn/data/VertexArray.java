package com.ciii.bobmu.ogllearn.data;

import android.opengl.GLES20;

import com.ciii.bobmu.ogllearn.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;


public class VertexArray {
    private FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer= ByteBuffer
                .allocateDirect(vertexData.length* Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride){
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount,  GLES20.GL_FLOAT, false, stride, floatBuffer);  //绘制顶点数组
        glEnableVertexAttribArray(attributeLocation);  //允许使用顶点数组
        floatBuffer.position(0);
    }
}
