package com.orange.plump.Solar;

import com.orange.plump.Solar.modules.RadioModule;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.net.URL;

public class RadioThread extends Thread {
	private final String url = "http://ice55.securenetsystems.net/DASH";
	public static Station currentStation;
	private static Player currentPlayer;
	public static boolean isPlaying;

	public void run() {
		isPlaying = true;
		try {
			currentPlayer = new Player(new BufferedInputStream(new URL(url + currentStation.getId()).openStream()));
			currentPlayer.setVolume(RadioModule.volume);
			while (isPlaying) {
				currentPlayer.play(1);
			}
			currentPlayer.close();
			currentPlayer = null;
		} catch (Exception e) {
			isPlaying = false;
			playStation(currentStation);
		}
	}

	public static void playStation(Station station) {
		if (currentStation == station && isPlaying == true) return;
		if (isPlaying) {
			pauseStation();
		}
		currentStation = station;
		Solar.radioThread = new RadioThread();
		Solar.radioThread.start();
	}

	public static void pauseStation() {
		isPlaying = false;
		if (Solar.radioThread != null) Solar.radioThread.stop();
	}

	public static void setVolume(float volume) {
		if (currentPlayer != null) currentPlayer.setVolume(volume);
	}

}

