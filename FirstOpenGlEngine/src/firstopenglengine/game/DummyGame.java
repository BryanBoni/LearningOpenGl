package firstopenglengine.game;

import firstopenglengine.engine.IGameLogic;
import firstopenglengine.engine.Window;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 * At the end we only need to create or game logic class, 
 * which for this chapter will be a simpler one.
 * It will just update the increase / decrease the clear color of the window whenever the user presses the up / down key.
 * The render method will just clear the window with that color.
 * 
 * 
 * 
 * @author Bryan Boni
 */
public class DummyGame implements IGameLogic {

    private int direction = 0;

    private float color = 0.0f;

    private final Renderer renderer;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            direction = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        window.setClearColor(color, color, color, 0.0f);
        renderer.clear();
    }    
}
