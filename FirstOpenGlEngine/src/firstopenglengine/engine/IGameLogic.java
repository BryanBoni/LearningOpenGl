package firstopenglengine.engine;

/**
 * Make the game engine reusable across different titles.
 * 
 * implement in all main game class.
 * 
 * @author Bryan Boni
 */
public interface IGameLogic {
    
    void init() throws Exception;
    
    void input(Window window);
    
    void update(float interval);
    
    void render(Window window);
}
