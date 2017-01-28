package firstopenglengine.engine.audio;

import firstopenglengine.engine.math.Vector3f;

import static org.lwjgl.openal.AL10.*;

/**
 *
 * @author Cloud4Msi
 */
public class SoundSource {
    
    /*
    Reminder, the sound control actions are made over a source and not from a buffer.
    */

    private final int sourceId;

    /**
     * 
     * @param loop use to tell if the sound have to play again an again or not
     * @param relative controls if the position of the source is relative to the listener (player) or not.
     */
    public SoundSource(boolean loop, boolean relative) {
        this.sourceId = alGenSources(); //generate a openAl source.

        if (loop) {
            alSourcei(sourceId, AL_LOOPING, AL_TRUE); //tell to set the looping mode at true.
        }
        if (relative) {
            alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
        }
    }

    /**
     * 
     * @param bufferId 
     */
    public void setBuffer(int bufferId) {
        stop();
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    /**
     * 
     * @param position 
     */
    public void setPosition(Vector3f position) {
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }

    /**
     * 
     * @param speed 
     */
    public void setSpeed(Vector3f speed) {
        alSource3f(sourceId, AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    /**
     * 
     * @param gain 
     */
    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    /**
     * 
     * @param param
     * @param value 
     */
    public void setProperty(int param, float value) {
        alSourcef(sourceId, param, value);
    }

    /**
     * play the source ref by the sourceID
     */
    public void play() {
        alSourcePlay(sourceId);
    }

    /**
     * 
     * @return 
     */
    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    /**
     *  pause the source ref by the sourceID
     */
    public void pause() {
        alSourcePause(sourceId);
    }

    /**
     * stop the source ref by the sourceID
     */
    public void stop() {
        alSourceStop(sourceId);
    }

    /**
     * delete the source ref by the sourceID,
     * and free the ressources used by it.
     */
    public void cleanup() {
        stop();
        alDeleteSources(sourceId);
    }

}
