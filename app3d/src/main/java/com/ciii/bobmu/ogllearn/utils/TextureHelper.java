package com.ciii.bobmu.ogllearn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.support.annotation.DrawableRes;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;

public class TextureHelper {
    public static int loadTexture(Context context, @DrawableRes int resId){
        int[] textureObjectId=new int[1];
        glGenTextures(1, textureObjectId, 0);
        if(textureObjectId[0]==0){
            LogUtil.i("TAG", "TextureHelper.loadTexture: Could not generate a new OpenGL texture object.");;
            return 0;
        }
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inScaled=false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if(bitmap==null){
            LogUtil.w("TAG", "TextureHelper.loadTexture: resourceId="+resId+" could not be decoded.");
            glDeleteTextures(1, textureObjectId, 0);
            return 0;
        }
        glBindTexture(GL_TEXTURE_2D, textureObjectId[0]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);  //设置缩小过滤器为三线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);  //设置放大过滤器为双线性过滤
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureObjectId[0];
    }
}
