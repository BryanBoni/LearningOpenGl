package firstopenglengine.engine.audio;

import firstopenglengine.engine.math.Vector3f;

import static org.lwjgl.openal.AL10.*;

/**
 *
 * @author Cloud4Msi
 */
public class SoundListener {

    /**
     * 
     */
    public SoundListener() {
        this(new Vector3f(0, 0, 0));
    }
    
    /**
     * 
     * @param position 
     */
    public SoundListener(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }
    
    /**
     * 
     * @param speed 
     */
    public void setSpeed(Vector3f speed){
        alListener3f(AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    /**
     * 
     * @param position
     */
    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }
    
    /**
     * 
     * @param at
     * @param up
     */
    public void setOrientation(Vector3f at, Vector3f up){
        float[] data = new float[6];
        
        data[0] = at.x;
        data[1] = at.y;
        data[2] = at.z;
        
        data[3] = up.x;
        data[4] = up.y;
        data[5] = up.z;
        
        alListenerfv(AL_ORIENTATION, data);
    }
}
