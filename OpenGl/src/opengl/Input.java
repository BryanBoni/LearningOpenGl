package opengl;

import org.lwjgl.BufferUtils;
import java.nio.DoubleBuffer;
import static opengl.HelloWorld.windowID;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Input {

    private static double MOUSE_X;
    private static double MOUSE_Y;

    public Input() {
    }

    public static void updateInputKey() {

    }

    public static void updateMouseCoordinate() {
        /*
        this function is used to retrieve informations about the coordinate of
        the mouse each frame.
         */
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(windowID, b1, b2);

        MOUSE_X = b1.get();
        MOUSE_Y = b2.get();
    }

    public static double getMouseX() {
        return MOUSE_X;
    }

    public static double getMouseY() {
        return MOUSE_Y;
    }
}
