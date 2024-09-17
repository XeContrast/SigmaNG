package info.sigmaclient.sigma.sigma5.jellomusic.player;

import info.sigmaclient.sigma.sigma5.jellomusic.AudioProcessor;
import info.sigmaclient.sigma.utils.render.rendermanagers.GlStateManager;
import info.sigmaclient.sigma.utils.render.rendermanagers.ScaledResolution;
import info.sigmaclient.sigma.gui.font.JelloFontUtil;
import info.sigmaclient.sigma.gui.clickgui.musicplayer.MusicPlayer;
import info.sigmaclient.sigma.sigma5.jellomusic.MusicUtils;
import info.sigmaclient.sigma.sigma5.jellomusic.Player;
import info.sigmaclient.sigma.sigma5.utils.ColorUtils;
import info.sigmaclient.sigma.modules.gui.hide.ClickGUI;
import info.sigmaclient.sigma.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import org.lwjgl.opengl.GL11;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static info.sigmaclient.sigma.minimap.minimap.Minimap.mc;
import static info.sigmaclient.sigma.sigma5.utils.Sigma5DrawText.drawString;
import static info.sigmaclient.sigma.gui.Sigma5LoadProgressGui.applyColor;


public class JelloMusic {
  static public List<double[]> spectrumData;
  public ArrayList<Double> spectrum;

  private static final long serialVersionUID = 1L;

  private static final Random RANDOM = new Random();
  public static MusicUtils musicUtils = new MusicUtils();
  public void getBlur(){
  }
  public List<Object> playlist = new ArrayList<Object>();

  private transient boolean isPaused = false;
  private transient boolean isStopped = true;

  private transient volatile int volume = 50;

  private transient volatile boolean shuffle = false;
  private transient volatile boolean repeat = true;

  private transient volatile Thread playingThread = null;
  private transient volatile int playingIndex = 0;
  private transient volatile SourceDataLine playingSource = null;
  private transient volatile int playingSourceVolume = 0;
  public static URL songurl;
  public static String durationInSeconds = "0:00";
  public InputStream inputStreamVar;
  {
    spectrumData = new ArrayList<>();
    this.spectrum = new ArrayList<>();
  }
  public JelloMusic() {
    init();
  }

  public JelloMusic(File file) {
    add(file);
    init();
  }

  public JelloMusic(File... files) {

    for (File file : files) {
      add(file);
    }

    init();
  }

  public JelloMusic(URL url) {
	//this.stop();
    add(url);
    songurl = url;
    init();
  }

  public JelloMusic(URL... urls) {

    for (URL url : urls) {
      add(url);
    }

    init();
  }

  private void init() {
    new MP3PlayerThemeDefault().apply(this);
  }

  /**
   * Appends the specified file (or all the files, recursively only if
   * specified, if represents a folder) to the end of the play list.
   */
  public JelloMusic add(File file, boolean recursively) {

    if (file.isFile()) {
      if (file.getName().endsWith(".cla")) {
        synchronized (JelloMusic.this) {
          playlist.add(file);
        }
      }
    }

    else if (file.isDirectory()) {

      File[] files = file.listFiles();

      for (File file2 : files) {
        if (file2.isFile() || recursively) {
          add(file2, recursively);
        }
      }
    }

    else {
      throw new IllegalArgumentException("WTF is this? ( " + file + " )");
    }

    return this;
  }

  /**
   * Appends the specified file (or all the files, recursively, if represents a
   * folder) to the end of the play list.
   */
  public JelloMusic add(File file) {
    add(file, true);
    return this;
  }

  // todo 频谱
  public void drawWave() {
    if (this.spectrumData.size() == 0) {
      return;
    }
//    if (this.W蒕꿩待쇽 == null) {
//      return;
//    }
    if (this.spectrum.size() != 0) {
      ScaledResolution sr = new ScaledResolution(mc);
      final float n = 114.0f;
      final float n2 = (float)Math.ceil(sr.getScaledWidth() / n);
      for (int index = 0; index < n; ++index) {
        final float n3 = 1.0f - (index + 1) / n;
        final float n4 = ((float)(Math.sqrt(this.spectrum.get(index)) / 12.0) - 5.0f) * (sr.getScaledHeight() / 1080.0f);
        Color c = info.sigmaclient.sigma.utils.render.ColorUtils.interpolateColorsBackAndForth(16, index * 20,
                MusicPlayer.firstColor
                ,MusicPlayer.secondColor
                , false);
//        ColorUtils.揩ꁈ杭ใ蛊.哺卫콗鱀ಽ
        RenderUtils.drawRect(index * n2, sr.getScaledHeight() - n4, index * n2 + n2, sr.getScaledHeight(), applyColor(c.getRGB(), 0.7f));
      }
//      startStencil();
//      for (int index2 = 0; index2 < n; ++index2) {
//        final float n5 = ((float)(Math.sqrt(this.spectrum.get(index2)) / 12.0) - 5.0f) * (sr.getScaledHeight() / 1080.0f);
//        RenderUtils.drawRect(index2 * n2, sr.getScaledHeight() - n5, n2, n5, ColorUtils.white.哺卫콗鱀ಽ);
//      }
//      applyStencilOperation(BoxOutlineESP.StencilOperation.drawRectangle);
//      if (this.W蒕꿩待쇽 != null) {
//        if (this.붃渺Ꮀ䖼綋 != null) {
//          㹔펊콵湗贞聛(0.0f, 0.0f, (float)sr.getWidth(), (float)sr.getHeight(), this.붃渺Ꮀ䖼綋, 0.4f);
//        }
//      }
//      endStencil();
    }
  }
  public void drawTexture(){
    if (this.spectrumData.size() == 0 || spectrum.size() < 3) {
      return;
    }
    ScaledResolution sr = new ScaledResolution(mc);
    double max = 0.0;
    final int n6 = 4750;
    for (int i = 0; i < 3; ++i) {
      max = Math.max(max, Math.sqrt(this.spectrum.get(i)) - 1000.0);
    }
    final float n7 = 1.0f + Math.round((float)(max / (n6 - 1000)) * 0.14f * 75.0f) / 75.0f;
//      RenderUtils.drawShadow(8,
//              ey - 8 - 50,8 + 50,
//              ey - 8 - 50 + 50, 0.5f);
    GlStateManager.color(1,1,1,1);
    GL11.glPushMatrix();
    GL11.glTranslated(60.0 / 2f, (double)(sr.getScaledHeight() - 55 / 2f), 0.0);
    GL11.glScalef(n7, n7, 0.0f);
    GL11.glTranslated(-60.0 / 2f, (double)(-(sr.getScaledHeight() - 55 / 2f)), 0.0);
//      drawTextureSigma(10.0f / 2f, (float)(sr.getScaledHeight() - 110), 100.0f / 2f, 100.0f / 2f, this.W蒕꿩待쇽);
    if(ClickGUI.clickGui.musicPlayer.currentFile.textureImage != null) {
      ClickGUI.clickGui.musicPlayer.currentFile.textureImage.rectTextureMasked2(
              10.0f / 2f, (float)(sr.getScaledHeight() - 110 / 2f),
              50,50,50 * 1.5f, 50 * 1.33f, (50 * 1.5f - 50) / 2f ,(50 * 1.33f - 50) / 2f);
    }
    RenderUtils.drawShadowWithAlpha(10.0f / 2f, (float)(sr.getScaledHeight() - 110 / 2f), 100.0f / 2f, 100.0f / 2f, 14.0f / 2f, 0.3f);
    GL11.glPopMatrix();
//      final String[] split = this.늦鱀햠㔢埙.split(ࡅ牰Ꮤ䆧卫.哝弻觯뎫霥[5]);
//      if (split.length <= 1) {
//      drawString(JelloFontUtil.jelloFontBold20, 130.0f / 2f, (float)(sr.getScaledHeight() - 81 / 2f), ClickGUI.clickGui.musicPlayer.currentFile.name, applyAlpha(ColorUtils.black.哺卫콗鱀ಽ, 0.4f));
//      drawString(JelloFontUtil.jelloFontBold18, 130.0f / 2f, (float)(sr.getScaledHeight() - 56 / 2f), ClickGUI.clickGui.musicPlayer.currentFile.aliasName, applyAlpha(ColorUtils.black.哺卫콗鱀ಽ, 0.5f));

    drawString(JelloFontUtil.jelloFontBold20, 130.0f / 2f, (float)(sr.getScaledHeight() - 81 / 2f), ClickGUI.clickGui.musicPlayer.currentFile.name, applyColor(ColorUtils.white.哺卫콗鱀ಽ, 0.7f));
    drawString(JelloFontUtil.jelloFont18, 130.0f / 2f, (float)(sr.getScaledHeight() - 56 / 2f), ClickGUI.clickGui.musicPlayer.currentFile.aliasName, applyColor(ColorUtils.white.哺卫콗鱀ಽ, 0.6f));
//      }
//      else {
//        drawString(蕃眓붛陬室.浦걾䎰ꁈ啖, 130.0f, (float)(sr.getHeight() - 81), split[0], 堧鏟ᔎ㕠釒.applyAlpha(ColorUtils.black.哺卫콗鱀ಽ, 0.4f));
//        drawString(蕃眓붛陬室.嶗롤랾蒕딨, 130.0f, (float)(sr.getHeight() - 56), split[1], 堧鏟ᔎ㕠釒.applyAlpha(ColorUtils.black.哺卫콗鱀ಽ, 0.5f));
//        drawString(蕃眓붛陬室.㮃㻣퉧蓳髾, 130.0f, (float)(sr.getHeight() - 56), split[1], 堧鏟ᔎ㕠釒.applyAlpha(ColorUtils.white.哺卫콗鱀ಽ, 0.7f));
//        drawString(蕃眓붛陬室.핇鱀䄟鄡W, 130.0f, (float)(sr.getHeight() - 81), split[0], 堧鏟ᔎ㕠釒.applyAlpha(ColorUtils.white.哺卫콗鱀ಽ, 0.6f));
//      }
  }
  /**
   * Appends the specified URL to the end of the play list.
   */
  public JelloMusic add(URL url) {
    synchronized (JelloMusic.this) {
      playlist.add(url);
    }
    return this;
  }
  boolean drawSpectrum = true;
  public void setDrawSpectrum(boolean t){
    drawSpectrum = t;
  }
  public void drawLines(){
    if (this.drawSpectrum) {
      if (this.spectrumData.size() != 0) {
        final double[] array = this.spectrumData.get(0);
        if (this.spectrum.isEmpty()) {
          for (int i = 0; i < array.length; ++i) {
            if (this.spectrum.size() < 1024) {
              this.spectrum.add(array[i]);
            }
          }
        }
        final float n = 60.0f;
        final float n2 = n / Minecraft.debugFPS;
        for (int j = 0; j < array.length; ++j) {
          final double n3 = this.spectrum.get(j) - array[j];
          final boolean b = this.spectrum.get(j) >= Double.MAX_VALUE;
          this.spectrum.set(j, Math.min(2.256E7, Math.max(0.0, this.spectrum.get(j) - n3 * Math.min(0.335f * n2, 1.0f))));
          if (b) {
            this.spectrum.set(j, 0.0);
          }
        }
        if (this.spectrum.isEmpty()) {
          return;
        }
      }
    }
  }

  /**
   * Starts the play (or resume if is paused).
   */
  public void play() {
    System.out.println("Write buffer");
    synchronized (JelloMusic.this) {
      if (isPaused) {
        isPaused = false;
        Player.playerPlaying = !isStopped;
        JelloMusic.this.notifyAll();
        return;
      }
    }
    System.out.println("Write buffer");

    stop();
    
    if (playlist.size() == 0) {
      return;
    }
    System.out.println("Write buffer");

    synchronized (JelloMusic.this) {
      isStopped = false;
      Player.playerStopped = false;
      Player.playerPlaying = !isPaused && !isStopped;
    }

    if (playingThread == null) {
      System.out.println("Write buffer");
      playingThread = new Thread(() -> {
        InputStream inputStream = null;
        try {
          Object playlistObject;

          synchronized (JelloMusic.this) {
            playlistObject = playlist.get(playingIndex);
          }

          if (playlistObject instanceof File) {
            inputStream = Files.newInputStream(((File) playlistObject).toPath());
          } else if (playlistObject instanceof URL) {
            inputStream = ((URL) playlistObject).openStream();
            inputStreamVar = ((URL) playlistObject).openStream();
          } else {
            throw new IOException("this is impossible; how come the play list contains this kind of object? :: " + playlistObject.getClass());
          }
          Movie movie = new MP4Container(inputStream).getMovie();
          AudioTrack track = (AudioTrack) movie.getTracks().get(1);
          net.sourceforge.jaad.aac.Decoder decoder = new net.sourceforge.jaad.aac.Decoder(track.getDecoderSpecificInfo());
          SampleBuffer sampleBuffer = new SampleBuffer();
          try {
            AudioFormat format = new AudioFormat(track.getSampleRate(),track.getSampleSize(),  track.getChannelCount(), true, true);
            (this.playingSource = AudioSystem.getSourceDataLine(format)).open();
            playingSource.start();
            while (true) {
              synchronized (JelloMusic.this) {
                if (isStopped) {
                  break;
                }
                if (isPaused) {
                  if (playingSource != null) {
                    playingSource.flush();
                  }
                  playingSourceVolume = volume;
                  try {
                    JelloMusic.this.wait();
                  } catch (InterruptedException ignored) {
                  }
                  continue;
                }
              }
              Frame f = track.readNextFrame();
              if (f == null) {
                break;
              }
              decoder.decodeFrame(f.getData(), sampleBuffer);
              final byte[] array3 = sampleBuffer.getData();
//              System.out.println("Write buffer");
              playingSource.write(array3, 0, array3.length);
              final float[] array4 = MusicUtils.䣓랾啖鄡䈔(sampleBuffer.getData(), format);
//              for(float fa : array4) {
//                System.out.println(array4.length);
//              }
              final float[][] array5 = new AudioProcessor(array4.length).錌浣䢶ᜄ㔢(array4);
              spectrumData.add(musicUtils.㐖䆧쿨콵浦(array5[0], array5[1]));
              if (spectrumData.size() > 18) {
                spectrumData.remove(0);
              }
              musicUtils.volumeLevel(playingSource, volume);
            }
            playingSource.close();
          }catch (Exception e){
            e.printStackTrace();
          }

          //
          // source is null at this point only if first frame is null
          // this means that probably the file is not a mp3

          if (playingSource == null) {
          }else {
            synchronized (JelloMusic.this) {
              if (!isStopped) {
                playingSource.drain();
              } else {
                playingSource.flush();
              }
            }

            playingSource.stop();
            playingSource.close();

            playingSource = null;
          }
        }

        catch (IOException e) {
          e.printStackTrace();
        }

        finally {
          if (inputStream != null) {
            try {
              inputStream.close();
            } catch (Exception e) {
            }
          }
        }

        boolean skipForwardAllowed;

        synchronized (JelloMusic.this) {

          //
          // take the value before reset

          skipForwardAllowed = !isStopped;

          //
          // reset values

          isPaused = false;
          isStopped = true;
          Player.playerPlaying = false;
          Player.playerStopped = true;
        }

        playingThread = null;

        if (skipForwardAllowed) {
          skipForward();
        }
      });

      playingThread.start();
    }
  }

  public boolean isPlaying() {
    synchronized (JelloMusic.this) {
      return !isPaused && !isStopped;
    }
  }

  public void pause() {

    if (!isPlaying()) {
      return;
    }

    synchronized (JelloMusic.this) {
      isPaused = true;
      Player.playerPlaying = !isPaused && !isStopped;
      JelloMusic.this.notifyAll();
    }
  }

  public boolean isPaused() {
    synchronized (JelloMusic.this) {
      return isPaused;
    }
  }

  public void stop() {

    synchronized (JelloMusic.this) {
      isPaused = false;
      isStopped = true;
      Player.playerStopped = true;
      Player.playerPlaying = !isPaused && !isStopped;
      JelloMusic.this.notifyAll();
    }

    if (playingThread != null) {
      try {
        playingThread.join();
      } catch (InterruptedException e) {
      }
    }
  }

  public boolean isStopped() {
    synchronized (JelloMusic.this) {
      return isStopped;
    }
  }

  /**
   * Forces the player to play next mp3 in the play list (or random if shuffle
   * is turned on).
   * 
   * @see #play()
   */
  public void skipForward() {
    skip(1);
  }

  /**
   * Forces the player to play previous mp3 in the play list (or random if
   * shuffle is turned on).
   * 
   * @see #play()
   */
  public void skipBackward() {
    skip(-1);
  }

  private void skip(int items) {

    if (playlist.size() == 0) {
      return;
    }

    boolean playAllowed = isPlaying();

    if (shuffle) {
      playingIndex = RANDOM.nextInt(playlist.size());
    }

    else {

      playingIndex += items;

      if (playingIndex > playlist.size() - 1) {
        if (repeat) {
          playingIndex = 0;
        } else {
          playingIndex = playlist.size() - 1;
          playAllowed = false;
        }
      } else if (playingIndex < 0) {
        if (repeat) {
          playingIndex = playlist.size() - 1;
        } else {
          playingIndex = 0;
          playAllowed = false;
        }
      }
    }

    stop();

    if (playAllowed) {
      play();
    }
  }

  /**
   * Sets a new volume for this player. The value is actually the percent value,
   * so the value must be in interval [0..100].
   * 
   * @param volume
   *          the new volume
   * 
   * @throws IllegalArgumentException
   *           if the volume is not in interval [0..100]
   */
  public JelloMusic setVolume(int volume) {

    if (volume < 0 || volume > 100) {
      throw new IllegalArgumentException("Wrong value for volume, must be in interval [0..100].");
    }

    this.volume = volume;

    return this;
  }

  /**
   * Returns the actual volume.
   */
  public int getVolume() {
    return volume;
  }

  /**
   * When you turn on shuffle, the next mp3 to play will be randomly chosen from
   * the play list.
   * 
   * @param shuffle
   *          true if shuffle should be turned on, or false for turning off
   */
  public JelloMusic setShuffle(boolean shuffle) {

    this.shuffle = shuffle;

    return this;
  }

  /**
   * Returns the shuffle state of the player. True if the shuffle is on, false
   * if it's not.
   * 
   * @return true if the shuffle is on, false otherwise
   */
  public boolean isShuffle() {
    return shuffle;
  }

  /**
   * When you turn on repeat, the player will practically never stop. After the
   * last mp3 from the play list will finish, the first will be automatically
   * played, or a random one if shuffle is on.
   * 
   * @param repeat
   *          true if repeat should be turned on, or false for turning off
   */
  public JelloMusic setRepeat(boolean repeat) {

    this.repeat = repeat;

    return this;
  }

  /**
   * Returns the repeat state of the player. True if the repeat is on, false if
   * it's not.
   * 
   * @return true if the repeat is on, false otherwise
   */
  public boolean isRepeat() {
    return repeat;
  }

  private void setVolume(SourceDataLine source, int volume) {

    try {

      FloatControl gainControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
      BooleanControl muteControl = (BooleanControl) source.getControl(BooleanControl.Type.MUTE);

      if (volume == 0) {
        muteControl.setValue(true);
      } else {
        muteControl.setValue(false);
        gainControl.setValue((float) (Math.log(volume / 100d) / Math.log(10.0) * 20.0));
      }
    }

    catch (Exception e) {
    }
  }

  /**
   * Retrieves the position in milliseconds of the current audio sample being
   * played. This method delegates to the <code>
   * AudioDevice</code> that is used by this player to sound the decoded audio
   * samples.
   */
  public void setPosition(int ms) {
    int pos = 0;
    if (playingSource != null) {
      pos = (int) (playingSource.getMicrosecondPosition() / 1000);
    }
  }
  /**
   * Retrieves the position in milliseconds of the current audio sample being
   * played. This method delegates to the <code>
   * AudioDevice</code> that is used by this player to sound the decoded audio
   * samples.
   */
  public int getPosition() {
    int pos = 0;
    if (playingSource != null) {
      pos = (int) (playingSource.getMicrosecondPosition() / 1000);
    }
    return pos;
  }

  public int getDurationInt(){
      BufferedInputStream bis = new BufferedInputStream(inputStreamVar);
      //AudioInputStream sound = AudioSystem.getAudioInputStream(bis);
      AudioInputStream audioInputStream;
      try {
        audioInputStream = AudioSystem.getAudioInputStream(bis);
      } catch (UnsupportedAudioFileException | IOException e) {
        throw new RuntimeException(e);
      }
    AudioFormat format2 = audioInputStream.getFormat();
      long frames = audioInputStream.getFrameLength();
      return (int) ((frames+0.0) / format2.getFrameRate() * 1000);
  }
  public void getDuration() throws UnsupportedAudioFileException, IOException{
	  if (playingSource.getFramePosition() > 0) {
		   BufferedInputStream bis = new BufferedInputStream(inputStreamVar);
	        //AudioInputStream sound = AudioSystem.getAudioInputStream(bis);
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
          AudioFormat format2 = audioInputStream.getFormat();
          long frames = audioInputStream.getFrameLength();
          durationInSeconds = String.valueOf((frames+0.0) / format2.getFrameRate());  
          }
  }
  private byte[] toByteArray(short[] ss, int offs, int len) {
    byte[] bb = new byte[len * 2];
    int idx = 0;
    short s;
    while (len-- > 0) {
      s = ss[offs++];
      bb[idx++] = (byte) s;
      bb[idx++] = (byte) (s >>> 8);
    }
    return bb;
  }

  private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
    objectInputStream.defaultReadObject();
  }

}
