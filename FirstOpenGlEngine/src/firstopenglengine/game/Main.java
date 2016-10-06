package firstopenglengine.game;

import firstopenglengine.engine.GameEngine;
import firstopenglengine.engine.IGameLogic;

/**
 * launch the game.
 */
public class Main {

    public static void main(String[] args) {
        try{
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("GAME", 600, 480, vSync, gameLogic);
            gameEng.start();
            
        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}