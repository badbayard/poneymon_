
package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.util.Duration;

/**
 * Controller of the sound.
 */
class SoundController {
    //Music file to play
    private static final String MUSIC_FILE =
                                    "src/main/resources/assets/BoostPoney.wav";
    // Player of the sound
    private final MediaPlayer boostPoney;

    /**
     * SoundController constructor.
     */
    SoundController() {
        Media sound = new Media(new File(MUSIC_FILE).toURI().toString());
        boostPoney = new MediaPlayer(sound);
    }

    /**
     * Plays a sound when a poney turn into nian poney.
     */
    void playBoostSound() {
        boostPoney.play();
        reset();
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
