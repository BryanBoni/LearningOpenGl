package opengl;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*; //for the hints gl
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL45.*; //gl version
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

    public static long windowID;
    private float rotateX, rotateY, rotateZ; //set up rotation
    private int width = 640, height = 480;

    public HelloWorld() {
        /*
        Before using GLFW, we have to initialise it. This is done by calling glfwInit() function. 
        it return 0 if there is any error, which is also the value of GL_FALSE,
        the constant we import from the GL11 class. Here is how you initialise the library.
         */
        if (glfwInit() != GL_TRUE) {
            System.err.println("Error initializing GLFW");
            System.exit(1);
        }
        /*
        By default, GLFW creates compatibility OpenGL contexts, which are often limited to OpenGL 2.1 and are deprecated.
        To make GLFW create a core context for us, we need to specify some window hints to it. 
        There are a lot of window hints, but most are self explanatory, so I’d leave the explanation of 
        the code to you to research.
         */
        //always before
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        //initiate a new window
        windowID = glfwCreateWindow(width, height, "My GLFW Window", NULL, NULL);

        if (windowID == NULL) {
            System.err.println("Error creating a window");
            System.exit(1);
        }

        //The first line specifies GLFW to make the context of the window whose handle is windowID the current one.
        glfwMakeContextCurrent(windowID);
        //The second line creates the LWJGL context from the current GLFW context, Creates a new GLCapabilities instance for the OpenGL context that is current in the current thread. 
        GL.createCapabilities();
        //There is this third-line with the glfwSwapInterval() function, this specifies that the context should refresh immediately when the buffers are swapped.
        glfwSwapInterval(1);

    }

    public void init() {
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
    }

    public void render() {

    }

    public void update(float delta) {
        //rotateX = Mouse.getX(); 
        Input.updateInputKey();
        Input.updateMouseCoordinate();//upadate the information about the mouse axe

        if (glfwGetKey(windowID, GLFW_KEY_SPACE) == GLFW_PRESS) {//send an int constant
            System.out.println("key space pressed");
        }
        System.out.println("mouse Axes: X: " + Input.getMouseX() + "Y: " + Input.getMouseY());
    }

    public void render(float delta) {
    }

    public void dispose() {
    }

    public void start() {
        float now, last, delta;

        last = 0;

        // Initialise the Game
        init();

        // Loop continuously and render and update
        while (glfwWindowShouldClose(windowID) != GL_TRUE) {
            // Get the time
            now = (float) glfwGetTime();
            delta = now - last;
            last = now;

            // Update and render
            update(delta);
            render(delta);

            // Poll the events and swap the buffers
            glfwPollEvents();
            glfwSwapBuffers(windowID);
        }

        // Dispose the game
        dispose();

        //to close the window
        glfwDestroyWindow(windowID);
        //to terminate GLFW and clean up any resources it use
        glfwTerminate();

        System.exit(0);
    }

    public static void main(String[] args) {
        new HelloWorld().start();
    }

}
