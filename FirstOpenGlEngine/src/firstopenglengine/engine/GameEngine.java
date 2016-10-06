package firstopenglengine.engine;

import static org.lwjgl.opengl.NVXGPUMemoryInfo.GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX;

/**
 * The engine of the game, where the game loop is played, GameEngine class
 * provides a start method which just starts our Thread so run method will be
 * executed asynchronously.
 *
 * @author Bryan Boni
 */
public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 75;//target Frames Per Second.

    public static final int TARGET_UPS = 30;//target Update Per Second.

    private final Thread gameLoopThread;

    private final Window window;

    private final IGameLogic gameLogic;

    private final Timer timer;

    public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    /**
     * In Windows and Linux platforms, 
     * although we are not using the main thread to initialize the GLFW stuff the samples will work. 
     * The problem is with OSX, so we need to change the source code of the run method of the GameEngine class to support that platform like this.
     */
    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init();
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isvSync()) {
                sync();
            }
            
            System.out.println("Memoire disponible du GPU :" + GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX);
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval) {
        gameLogic.update(interval);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }
}
