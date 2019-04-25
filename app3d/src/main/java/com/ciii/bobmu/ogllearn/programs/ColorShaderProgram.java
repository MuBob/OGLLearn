package com.ciii.bobmu.ogllearn.programs;

import android.content.Context;

import com.ciii.bobmu.ogllearn.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class ColorShaderProgram extends ShaderProgram {
    //Uniform locations
    private final  int uMatrixLocation;

    //Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
        uMatrixLocation= glGetUniformLocation(program, U_MATRIX);
        aPositionLocation=glGetAttribLocation(program, A_POSITION);
        aColorLocation=glGetAttribLocation(program, A_COLOR);
    }
    public void setUniforms(float[] matrix){
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }

    public int getColorAttributeLocation(){
        return aColorLocation;
    }
}
