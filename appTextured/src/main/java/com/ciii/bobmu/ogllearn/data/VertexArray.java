package com.ciii.bobmu.ogllearn.data;

import android.opengl.GLES20;

import com.ciii.bobmu.ogllearn.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
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
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }



}
