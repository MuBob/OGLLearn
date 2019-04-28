package com.ciii.bobmu.ogllearn.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {
    public static String readTextFromResource(Context context, @RawRes int resId){
        StringBuilder body=new StringBuilder();
        try {
            InputStream inputStream=context.getResources().openRawResource(resId);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine=bufferedReader.readLine())!=null){
                body.append(nextLine);
                body.append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
            LogUtil.e("TAG", "TextResourceReader.readTextFromResource: 资源文件未找到");
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
            LogUtil.e("TAG", "TextResourceReader.readTextFromResource: 资源文件未找到");
        }
        return body.toString();
    }
}
