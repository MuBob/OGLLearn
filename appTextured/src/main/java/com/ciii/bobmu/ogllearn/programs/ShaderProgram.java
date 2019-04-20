package com.ciii.bobmu.ogllearn.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.ciii.bobmu.ogllearn.utils.ShaderHelper;
import com.ciii.bobmu.ogllearn.utils.TextResourceReader;

/**
 * Created by bob on 2019/3/30.
 */

public class ShaderProgram {
    //Uniform constants
    protected static final String U_MATRIX="u_Matrix";
    protected static final String U_TEXTURE_UNIT="u_TextureUnit";
    //Attribute constants
    protected static final String A_POSITION="a_Position";
    protected static final String A_COLOR="a_Color";
    protected static final String A_TEXTURE_COORDINATES="a_TextureCoordinates";
    //Shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){
        program= ShaderHelper.buildProgram(
                TextResourceReader.readTextFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFromResource(context, fragmentShaderResourceId)
        );
    }

    public void useProgram(){
        GLES20.glUseProgram(program);
    }
}
