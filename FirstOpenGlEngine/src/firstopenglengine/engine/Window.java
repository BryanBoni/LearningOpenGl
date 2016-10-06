package firstopenglengine.engine;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Main window of the game.
 * 
 * @author Bryan Boni.
 */
public class Window {

    private final String title;

    private int width;

    private int height;

    private boolean vSync;
    
    private boolean resized;
    
    private long windowHandle;
    
    private GLFWErrorCallback errorCallback;

    private GLFWKeyCallback keyCallback;

    private GLFWWindowSizeCallback windowSizeCallback;

    public Window(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resized = false;
    }

    private void init() {
        
        // Setup resize callback
        glfwSetWindowSizeCallback(windowHandle, windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Window.this.width = width;
                Window.this.height = height;
                Window.this.setResized(true);
            }
        });

    }

   
    /**
     * Synchronise the frame window.
     */
    private void vSync() {
        glfwSwapInterval(1);//We are synchronizing to the refresh card of the video card, which at the end will result in a constant frame rate, 1 = 60fps, 2 = 30fps
        glfwSwapBuffers(windowHandle);//store information of screen update
    }

    /**
     * Method to detect key presses which will be used in the game loop.
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }
    
    
}
