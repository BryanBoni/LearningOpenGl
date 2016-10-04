package firstopenglengine.game;

import static org.lwjgl.glfw.GLFW.*;

/**
 * launch the game.
 */
public class Main {

    private void sync(double loopStartTime){
        float loopSlot = 1f / 50; //how many seconds the game loop iteration should last.
        
        double endTime = loopStartTime + loopSlot;
        
        while(getTime() < endTime){
            try{
                thread.sleep(1);
            }catch(InterruptedException ie){
                
            }
        }
    }
    
    private void vSync(){
        glfwSwapInterval(1);//We are synchronizing to the refresh card of the video card, which at the end will result in a constant frame rate
        glfwSwapBuffers(windowHandle);//store information of screen update
    }
    public static void main(String[] args) {
        double secsPerUpdate = 1 / 30;
        double previous = getTime();
        double steps = 0.0;
        while (true) {
            double loopStartTime = getTime();
            double elapsed = loopStartTime - previous;
            previous = current;
            steps += elapsed;

            handleInput();

            while (steps >= secsPerUpdate) {
                updateGameState();
                steps -= secsPerUpdate;
            }

            render();
            sync(current);
        }
    }
}
