package com.example.oops_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundManager {
    private AudioClip gameOverSound;
    private AudioClip eatSound;
    private MediaPlayer backgroundMusicPlayer;

    public SoundManager() {
        try {
            URL gameOverUrl = getClass().getResource("/sound.mp3");
            URL eatUrl = getClass().getResource("/gulp.mp3");
            URL musicUrl = getClass().getResource("/snake.mp3"); //  Background music

            if (gameOverUrl != null) {
                gameOverSound = new AudioClip(gameOverUrl.toString());
            }

            if (eatUrl != null) {
                eatSound = new AudioClip(eatUrl.toString());
            }

            if (musicUrl != null) {
                Media backgroundMedia = new Media(musicUrl.toString());
                backgroundMusicPlayer = new MediaPlayer(backgroundMedia);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            }

        } catch (Exception e) {
            System.out.println("Error loading sounds: " + e.getMessage());
        }
    }

    public void playGameOverSound() {
        if (gameOverSound != null) {
            gameOverSound.play();
        }
    }

    public void playEatSound() {
        if (eatSound != null) {
            eatSound.play();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public boolean isMusicPlaying() {
        return backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
