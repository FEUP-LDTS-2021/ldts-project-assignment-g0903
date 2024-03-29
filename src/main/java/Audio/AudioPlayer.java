package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class AudioPlayer {
    //singleton
    private static AudioPlayer instance;

    private final Clip menuMusic;
    private final Clip gameMusic;
    private final Clip explosionSound1;
    private final Clip explosionSound2;
    private final Clip hurtSound;
    private final Clip loseSound;
    private final Clip powerupSound;
    private final Clip shieldSound;

    private float musicVolume = 1.0f;
    private float fxVolume = 1.0f;

    public AudioPlayer() {
        this.menuMusic = loadMusic("/music/BombermanMusic.wav");
        this.gameMusic = loadMusic("/music/BombermanMusic2.wav");
        this.explosionSound1 = loadMusic("/soundfx/explosion.wav");
        this.explosionSound2 = loadMusic("/soundfx/explosion2.wav");
        this.hurtSound = loadMusic("/soundfx/hurt.wav");
        this.loseSound = loadMusic("/soundfx/lose.wav");
        this.powerupSound= loadMusic("/soundfx/powerup2.wav");
        this.shieldSound= loadMusic("/soundfx/shield.wav");
        setMusicVolume(0.5f);
        setFxVolume(0.8f, false);
    }

    public static AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    private Clip loadMusic(String filePath_) throws NullPointerException{
        try {
            File musicFile = new File(AudioPlayer.class.getResource(filePath_).getFile());
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            Clip musicClip = AudioSystem.getClip();
            musicClip.open(audioInput);
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-25.0f);
            return musicClip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startMenuMusic() {
        gameMusic.stop();
        menuMusic.setMicrosecondPosition(0);
        menuMusic.start();
        menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void startGameMusic() {
        menuMusic.stop();
        gameMusic.setMicrosecondPosition(0);
        gameMusic.start();
        gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }

    public void stopGameMusic() {
        gameMusic.stop();
    }

    public void playExplosionSound1() {
        explosionSound1.setMicrosecondPosition(0);
        explosionSound1.start();
        explosionSound1.loop(0);
    }

    public void playExplosionSound2() {
        explosionSound2.setMicrosecondPosition(0);
        explosionSound2.start();
        explosionSound2.loop(0);
    }

    public void playHurtSound() {
        hurtSound.setMicrosecondPosition(0);
        hurtSound.start();
        hurtSound.loop(0);
    }

    public void playLoseSound() {
        loseSound.setMicrosecondPosition(0);
        loseSound.start();
        loseSound.loop(0);
    }

    public void playPowerUpSound() {
        powerupSound.setMicrosecondPosition(0);
        powerupSound.start();
        powerupSound.loop(0);
    }

    public void playShieldSound() {
        shieldSound.setMicrosecondPosition(0);
        shieldSound.start();
        shieldSound.loop(0);
    }

    public void setMusicVolume(float volume_) {
        if (volume_ > 1.0f)
            volume_ = 1.0f;
        else if (volume_ < 0.0f)
            volume_ = 0.0f;
        musicVolume  = volume_;
        setClipVolume(menuMusic, musicVolume);
        setClipVolume(gameMusic, musicVolume);
    }

    public float getMusicVolume() {
        return this.musicVolume;
    }

    public void setFxVolume(float volume_, boolean ping_) {
        if (volume_ > 1.0f)
            volume_ = 1.0f;
        else if (volume_ < 0.0f)
            volume_ = 0.0f;
        fxVolume  = volume_;
        setClipVolume(explosionSound1, fxVolume);
        setClipVolume(explosionSound2, fxVolume);
        setClipVolume(hurtSound, fxVolume);
        setClipVolume(loseSound, fxVolume);
        setClipVolume(powerupSound, fxVolume);
        setClipVolume(shieldSound, fxVolume);
        if (!powerupSound.isActive() && ping_)
            playPowerUpSound();
    }

    public float getFxVolume() {
        return this.fxVolume;
    }

    private void setClipVolume(Clip clip_, float volume_) {
        FloatControl gainControl = (FloatControl) clip_.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume_) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }
}

