package firstopenglengine.game;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import firstopenglengine.engine.Utils;
import firstopenglengine.engine.Window;
//import firstopenglengine.engine.graph.Mesh;
import firstopenglengine.engine.graph.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * The class who will do the game render logic.
 *
 * @author Bryan Boni.
 */
public class Renderer {

    private int vboId;//reference the VBO, buffer of verticies
    private int vaoId;//reference the VAO, array of VBO
    private ShaderProgram shaderProgram;

    public Renderer() {

    }

    public void render(Window window) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        // Bind to the VAO
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        // Draw the vertices
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();

        //test
        float[] vertices = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        };

        //VAO contains some VBO witch is a memory buffer of vertices, VBO are the attribute of a VAO
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        //define the structure of our data and store in one of the attribute lists of the VAO
        /*
        The parameters are:
            index: Specifies the location where the shader expects this data.
            size: Specifies then number of components per vertex attribute (from 1 to 4). In this case, we are passing 3D coordinates, so it should be 3.
            type: Specifies the type of each component in the array, in this case a float.
            normalized: Specifies if the values should be normalized or not.
            stride: Specifies the byte offset between consecutive generic vertex attributes. (We will explain it later).
            offset: Specifies a offset of the first component of the first component in the array in the data store of the buffer.
         */
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        //once finish
        // Unbind the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        // Unbind the VAO
        glBindVertexArray(0);

    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }

        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

}
