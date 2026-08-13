import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMusic {
  private List<Clip> clipPool = new ArrayList<>();
  private Clip currentClip;
  private int currentFramePosition = 0;

  public GameMusic(String filePath, int poolSize) {
    try {
        for (int i = 0; i < poolSize; i++) {
            InputStream inputStream = ResourceLoaderUtil.getInputStreamFromFile(filePath);
            if(inputStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clipPool.add(clip);
            }
        }
    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
  }

  public void playLoop() {
    if (currentClip == null) {
      currentClip = clipPool.get(0);
    }
    if (!currentClip.isRunning()) {
      currentClip.setFramePosition(0);
      currentClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
  }

  public void pause() {
    if (currentClip != null && currentClip.isRunning()) {
      currentFramePosition = currentClip.getFramePosition();
      currentClip.stop();
    }
  }

  public void resume() {
    if (currentClip != null && !currentClip.isRunning()) {
      currentClip.setFramePosition(currentFramePosition);
      currentClip.loop(Clip.LOOP_CONTINUOUSLY);
      currentClip.start();
    }
  }

  public void play() {
    for (Clip clip : clipPool) {
      if (!clip.isRunning()) {
        clip.setFramePosition(0);
        clip.start();
        return;
      }
    }
  }
}
