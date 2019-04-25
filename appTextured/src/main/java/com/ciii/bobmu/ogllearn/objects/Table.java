package com.ciii.bobmu.ogllearn.objects;

import com.ciii.bobmu.ogllearn.Constants;
import com.ciii.bobmu.ogllearn.data.VertexArray;
import com.ciii.bobmu.ogllearn.programs.TextureShaderProgram;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by bob on 2019/3/30.
 */

public class Table {
    private static final int POSITION_COMPONENT_COUNT=2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT=2;
    private static final int STRIDE=(POSITION_COMPONENT_COUNT+TEXTURE_COORDINATES_COMPONENT_COUNT)* Constants.BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA={
            // Order of coordinates: X, Y, S, T
            //Triangle Fan
                0f,     0f, 0.5f, 0.5f,
            -0.5f, -0.8f,    0f, 0.9f,
             0.5f, -0.8f,    1f, 0.9f,
             0.5f,  0.8f,    1f, 0.1f,
            -0.5f,  0.8f,    0f, 0.1f,
            -0.5f, -0.8f,    0f, 0.9f
    };
    private final VertexArray vertexArray;
    public Table(){
        vertexArray=new VertexArray(VERTEX_DATA);
    }
    public void bindData(TextureShaderProgram textureShaderProgram){
        vertexArray.setVertexAttribPointer(
                0,
                textureShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureShaderProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);

    }

    public void draw(){
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }


}
