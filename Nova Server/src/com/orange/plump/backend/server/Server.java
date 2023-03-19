package com.orange.plump.backend.server;

import com.orange.plump.backend.ClientThread;
import com.orange.plump.backend.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Server extends Thread {
	
	public ServerSocket server;
	private int port;
	private boolean isGoing = false;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void run() {
		try {
			isGoing = true;

			server = new ServerSocket(port);
			Main.log("Server started!\n");

			while(isGoing) {
				Socket newClient = server.accept();
				newClient.setTcpNoDelay(true);
				newClient.setSoTimeout(1000);
				newClient.setKeepAlive(false);
				ClientThread.clients.add(newClient);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException {
		server.close();
		isGoing = false;
	}
	
	public static void remove(Socket client) {
		try {
			client.close();
			ClientThread.clients.remove(client);
		} catch (Exception e) {
			ClientThread.clients.remove(client);
		}
	}
	
	public void send(String ip, String s) throws IOException {
		ClientThread.send(ip, s);
	}
}
