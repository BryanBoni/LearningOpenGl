package firstopenglengine.engine.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import firstopenglengine.engine.math.Matrix4f;
import firstopenglengine.engine.math.Vector3f;
import java.util.AbstractList;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
//import firstopenglengine.engine.graph.Camera;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.AL10.*;

/**
 *
 * @author Cloud4Msi
 */
public class SoundManager {

    private long device;
    private long context;
    private SoundListener listener;
    private final List<SoundBuffer> soundBufferList;
    private final Map<String, SoundSource> soundSourceMap;
    
    /**
     * 
     */
    public SoundManager() {
        soundBufferList = new ArrayList<>();
        soundSourceMap = new HashMap<>();
    }

    /**
     * The init method initializes the OpenAL subsystem: 
     * - Opens the default device, 
     * - Create the capabilities for that device, 
     * - Create a sound context, like the OpenGL one, and set it as the current one.
     *
     * @throws java.lang.Exception
     */
    public void init() throws Exception {
        this.device = alcOpenDevice((ByteBuffer) null);

        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        this.context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    /**
     * 
     * @param name
     * @param soundSource 
     */
    public void addSoundSource(String name, SoundSource soundSource) {
        this.soundSourceMap.put(name, soundSource);
    }

    /**
     * 
     * @param name
     * @return 
     */
    public SoundSource getSoundSource(String name) {
        return this.soundSourceMap.get(name);
    }

    /**
     * 
     * @param name 
     */
    public void playSoundSource(String name) {
        SoundSource soundSource = this.soundSourceMap.get(name);
        if (soundSource != null && !soundSource.isPlaying()) {
            soundSource.play();
        }
    }

    /**
     * 
     * @param name 
     */
    public void removeSoundSource(String name) {
        this.soundSourceMap.remove(name);
    }

    /**
     * 
     * @param soundBuffer 
     */
    public void addSoundBuffer(SoundBuffer soundBuffer) {
        this.soundBufferList.add(soundBuffer);
    }

    /**
     * 
     * @return 
     */
    public SoundListener getListener() {
        return this.listener;
    }

    /**
     * 
     * @param listener 
     */
    public void setListener(SoundListener listener) {
        this.listener = listener;
    }

    /*
    public void updateListenerPosition(Camera camera) {
        Matrix4f cameraMatrix = camera.getViewMatrix();
        listener.setPosition(camera.getPosition());
        Vector3f at = new Vector3f();
        cameraMatrix.positiveZ(at).negate();
        Vector3f up = new Vector3f();
        cameraMatrix.positiveY(up);
        listener.setOrientation(at, up);
    }*/

    /**
     * 
     * @param model 
     */
    public void setAttenuationModel(int model) {
        alDistanceModel(model);
    }

    /**
     * 
     */
    public void cleanup() {
        for (SoundBuffer soundBuffer : soundBufferList) {
            soundBuffer.cleanup();
        }
        soundBufferList.clear();
        for (SoundSource soundSource : soundSourceMap.values()) {
            soundSource.cleanup();
        }
        soundSourceMap.clear();
        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (device != NULL) {
            alcCloseDevice(device);
        }
    }

}
