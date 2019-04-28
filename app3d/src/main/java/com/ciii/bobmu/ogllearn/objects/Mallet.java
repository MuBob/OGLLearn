package com.ciii.bobmu.ogllearn.objects;

import com.ciii.bobmu.ogllearn.data.VertexArray;
import com.ciii.bobmu.ogllearn.programs.ColorShaderProgram;
import com.ciii.bobmu.ogllearn.utils.Geometry;

import java.util.List;

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT=3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        ObjectBuilder.GeneratedData generatedData=ObjectBuilder.createMallet(
                new Geometry.Point(0f, 0f, 0f), radius, height, numPointsAroundMallet
        );
        this.radius = radius;
        this.height = height;
        vertexArray=new VertexArray(generatedData.vertexData);
        drawList=generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram){
        vertexArray.setVertexAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                0
        );
    }


    public void draw(){
        for (ObjectBuilder.DrawCommand drawCommand:drawList){
            drawCommand.draw();
        }
    }
   /* static final int POSITION_COMPONENT_COUNT=2;
    static final int COLOR_COMPONENT_COUNT=3;
    static final int STRIDE = (POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)* Constants.BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA={
            //Order of coordinates: X, Y, R, G, B
            0f, -0.4f, 1f, 0f, 0f,
            0f,  0.4f, 0f, 0f, 1f
    };
    private VertexArray vertexArray;

    public Mallet(){
        vertexArray=new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram) {
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
*/
}
