package firstopenglengine.engine.graph;

import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author Cloud4Msi
 */
public class ShaderProgram {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    /**
     * 
     * @throws Exception 
     */
    public ShaderProgram() throws Exception {
        programId = glCreateProgram();//create an empty object were a shader can be attached
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
    }

    /**
     * 
     * @param shaderCode the identifier/reference to the shader
     * @throws Exception 
     */
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    /**
     * 
     * @param shaderCode the identifier/reference to the shader
     * @throws Exception 
     */
    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    /**
     * 
     * @param shaderCode
     * @param shaderType
     * @return
     * @throws Exception 
     */
    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    /**
     * 
     * @throws Exception 
     */
    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

    }

    /**
     * 
     */
    public void bind() {
        glUseProgram(programId);
    }

    /**
     * 
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * 
     */
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
