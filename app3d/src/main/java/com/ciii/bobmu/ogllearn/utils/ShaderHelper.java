package com.ciii.bobmu.ogllearn.utils;

import static android.opengl.GLES20.*;

public class ShaderHelper {
    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }
    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }
    private static int compileShader(int type, String shaderCode){
        int shaderObjectId=glCreateShader(type);  //创建着色器对象
        if(shaderObjectId==0){
            LogUtil.w("TAG",
                    "ShaderHelper.compileShader: could not create new shader.");
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode);  //源码上传到着色器对象中
        glCompileShader(shaderObjectId);  //编译着色器对象
        final int[] compileStatus=new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);  //获取编译状态
        String s = glGetShaderInfoLog(shaderObjectId);
        LogUtil.i("TAG", "ShaderHelper.compileShader: log="+s);
        if(compileStatus[0]==0){
            glDeleteShader(shaderObjectId);
            LogUtil.w("TAG", "ShaderHelper.compileShader: Compilation of shader failed.");
            return 0;
        }
        return shaderObjectId;
    }
    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId=glCreateProgram();
        if(programObjectId==0){
            LogUtil.i("TAG", "ShaderHelper.linkProgram: could not create new shader.");
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);  //把顶点着色器附加到程序对象上
        glAttachShader(programObjectId, fragmentShaderId);  //把片段着色器附加到程序对象上
        glLinkProgram(programObjectId);  //链接程序
        final int[] linkStatus=new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        String s = glGetProgramInfoLog(programObjectId);
        LogUtil.i("TAG", "ShaderHelper.linkProgram: log="+s);
        if(linkStatus[0]==0){
            glDeleteShader(programObjectId);
            LogUtil.w("TAG", "ShaderHelper.linkProgram: Linking of program failed.");
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId){
        glValidateProgram(programObjectId);
        final int[] validataStatus=new int[1];
        String s = glGetProgramInfoLog(programObjectId);
        LogUtil.i("TAG", "ShaderHelper.validateProgram: log="+s);
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validataStatus, 0);
        LogUtil.i("TAG", "ShaderHelper.validateProgram: status="+validataStatus[0]);
        return validataStatus[0]!=0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource){
        int program;
        int vertexShader=compileVertexShader(vertexShaderSource);
        int fragmentShader=compileFragmentShader(fragmentShaderSource);
        program=linkProgram(vertexShader, fragmentShader);
        validateProgram(program);
        return program;
    }

}
