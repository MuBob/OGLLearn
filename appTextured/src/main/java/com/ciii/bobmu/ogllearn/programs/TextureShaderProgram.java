package com.ciii.bobmu.ogllearn.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.ciii.bobmu.ogllearn.R;

/**
 * 纹理加载程序
 * Created by bob on 2019/3/30.
 */

public class TextureShaderProgram extends ShaderProgram {
    //Uniform locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    //Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        //读入并保存 uniform locations
        uMatrixLocation= GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation=GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
        //读入并保存 attribute locations
        aPositionLocation=GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation=GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation(){
        return aTextureCoordinatesLocation;
    }
}
