package com.orange.plump.RadioSystemTest;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;

public class Application {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.95.58.242", 420);
			if (socket.isConnected()) {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF("NOVACLIENT$PACKET 0");
				InputStream in = new BufferedInputStream(socket.getInputStream());
				playSound(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//playSound("wish-you-were-gay.wav");
	}

	public static void playSound(InputStream stream) {
		class AudioListener implements LineListener {
			private boolean done = false;
			@Override public synchronized void update(LineEvent event) {
				LineEvent.Type eventType = event.getType();
				if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
					done = true;
					notifyAll();
				}
			}
			public synchronized void waitUntilDone() throws InterruptedException {
				while (!done) { wait(); }
			}
		}
		try {
			Clip clip = AudioSystem.getClip();
			AudioListener listener = new AudioListener();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(stream);
			clip.open(inputStream);
			clip.addLineListener(listener);
			clip.start();

			listener.waitUntilDone();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playSound(final String url) {
		class AudioListener implements LineListener {
			private boolean done = false;
			@Override public synchronized void update(LineEvent event) {
				LineEvent.Type eventType = event.getType();
				if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
					done = true;
					notifyAll();
				}
			}
			public synchronized void waitUntilDone() throws InterruptedException {
				while (!done) { wait(); }
			}
		}
		try {
			Clip clip = AudioSystem.getClip();
			AudioListener listener = new AudioListener();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(url));
			clip.open(inputStream);
			clip.addLineListener(listener);
			clip.start();

			listener.waitUntilDone();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
