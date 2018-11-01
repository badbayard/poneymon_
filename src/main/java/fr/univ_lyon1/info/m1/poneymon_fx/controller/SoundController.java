
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.util.Duration;

/**
 * Controller of the sound.
 */
public class SoundController {
    // Music file to play
    private static final String MUSIC_FILE = "src/main/resources/assets/sound/BoostPoney.wav";
    private static final String MUSIC_BACKGROUND = "src/main/resources/assets/sound/poney.mp3";
    // Player of the sound
    private final MediaPlayer boostPoney;
    private final MediaPlayer chunk;

    /**
     * SoundController constructor.
     */
    public SoundController() {
        Media sound = new Media(new File(MUSIC_FILE).toURI().toString());
        Media sound2 = new Media(new File(MUSIC_BACKGROUND).toURI().toString());
        boostPoney = new MediaPlayer(sound);
        chunk = new MediaPlayer(sound2);

    }

    /**
     * Plays a sound when a poney turn into nian poney.
     */
    void playBoostSound() {
        boostPoney.play();
        reset();
    }

    /**
     * TODO write the javadoc.
     */
    public void playchunk() {
        chunk.setOnEndOfMedia(() -> chunk.seek(Duration.ZERO));
        chunk.play();
    }

    /**
     * Sets the sound to the begining.
     */
    private void reset() {
        boostPoney.seek(Duration.ZERO);
    }

    /**
     * Pauses the sound.
     */
    void pause() {
        boostPoney.pause();
    }

    /**
     * Resumes the sound.
     */
    void resume() {
        boostPoney.play();
    }
}
