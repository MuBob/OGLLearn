package com.ciii.bobmu.ogllearn.objects;

import com.ciii.bobmu.ogllearn.Constants;
import com.ciii.bobmu.ogllearn.data.VertexArray;
import com.ciii.bobmu.ogllearn.programs.ColorSimpleShaderProgram;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;

public class SimpleMallet {
    static final int POSITION_COMPONENT_COUNT = 2;
    static final int COLOR_COMPONENT_COUNT = 3;
    static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
            //Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 1f, 0f, 0f,
            0f, 0.4f, 0f, 0f, 1f
    };
    private VertexArray vertexArray;

    public SimpleMallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorSimpleShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, 2);
    }
}
