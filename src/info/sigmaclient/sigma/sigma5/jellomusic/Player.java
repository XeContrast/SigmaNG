package info.sigmaclient.sigma.sigma5.jellomusic;



import info.sigmaclient.sigma.sigma5.jellomusic.player.JelloMusic;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import top.fl0wowp4rty.phantomshield.annotations.Native;


public class Player {

	public static JelloMusic player;
	//public static Song currentSong;
	public static boolean isPlaying = false;
	//public static List<Song> currentSongList = new CopyOnWriteArrayList<Song>();
	public static float volumeControl;
	public static boolean paused;
	public static boolean playerStopped = true;
	public static boolean playerPlaying;

	public static void resume() {
		if (player != null) {
			setVolume(volumeControl * 50);
			player.play();
			isPlaying = true;
			paused = false;
		}
	}

	public static void play(String url) {
		System.out.println(url);
		stop();

		URL u;
		try {
			u = new URL(url);
			player = new JelloMusic(u);
		} catch (MalformedURLException e1) {
		}

		player.setRepeat(false);
		setVolume(volumeControl * 50);
		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
					isPlaying = true;
					paused = false;
				} catch (Exception e) {
				}
			}
		}.start();
	}


	public static void playFile(File f) {
		stop();
		player = new JelloMusic(f);
		player.setRepeat(false);
		setVolume(volumeControl * 50);
		new Thread(() -> {
			try {
				player.play();
				isPlaying = true;
				paused = false;
			} catch (Exception e) {
			}
		}).start();
	}

	public static void pause() {
		if (player != null) {
			player.pause();
			isPlaying = false;
			paused = true;
		}
	}
	
	/*public static void next() {
		if (player != null) {
			int curInd = currentSongList.indexOf(currentSong);
			if (curInd == currentSongList.size()-1) {
				stop();
				return;
			}
			play(currentSongList, currentSongList.get(curInd+1));
		}
	}*/
	
	/*public static void prev() {
		if (player != null) {
			int curInd = currentSongList.indexOf(currentSong);
			if (curInd == 0) {
				stop();
				return;
			}
			play(currentSongList, currentSongList.get(curInd-1));
		}
	}*/

	public static boolean isPlaying() {
		return playerPlaying;
	}

	public static boolean isStopped() {
		return playerStopped;
	}

	public static void stop() {
		if (player != null) {
			player.stop();
			player = null;
			//currentSong = null;
			isPlaying = false;
			paused = false;
		}
	}

	public static void setVolume(float volume) {
		//vol = volume;
		if (player != null) {
			player.setVolume((int) volume);
		}
	}


}
