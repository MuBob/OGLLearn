package com.ciii.bobmu.ogllearn.data;

import com.ciii.bobmu.ogllearn.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;


/**
 * 存储顶点矩阵数据
 * Created by bob on 2019/3/30.
 */

public class VertexArray {
    private static final int BYTES_PER_FLOAT = Constants.BYTES_PER_FLOAT;
    private final FloatBuffer floatBuffer;
    public VertexArray(float[] vertexData){
        floatBuffer= ByteBuffer
                .allocateDirect(vertexData.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride){
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }



}
