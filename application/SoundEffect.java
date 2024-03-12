package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffect {
    private static MediaPlayer mediaPlayer;

    public static void playBgm(String src){
        Media media = new Media(SoundEffect.class.getResource(src).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop indefinitely
        mediaPlayer.play();
    }

    public static void stopBgm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

}
