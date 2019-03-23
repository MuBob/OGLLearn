package com.ciii.bobmu.ogllearn.utils;

/**
 * Created by bob on 2019/3/23.
 */

public class MatrixHelper {
    public static void perspectiveM(float[] m, float yFowInDegrees, float aspect, float n, float f){
        //第一步：计算焦距
        final  float angleInRadians= (float) (yFowInDegrees*Math.PI/180.0f);
        final float a= (float) (1.0f/Math.tan(angleInRadians/2.0f));
        m[0]=a/aspect;
        m[1]=0f;
        m[2]=0f;
        m[3]=0f;

        m[4]=0f;
        m[5]=a;
        m[6]=0f;
        m[7]=0f;

        m[8]=0f;
        m[9]=0f;
        m[10]=-((f+n)/(f-n));
        m[11]=-1f;
        m[12]=0f;
        m[13]=0f;
        m[14]=-((2f*f*n)/(f-n));
        m[15]=0f;
    }


}
