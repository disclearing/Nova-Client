package com.orange.plump.backend;

import com.orange.plump.backend.server.Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {

	private boolean isGoing = false;
	public static List<Socket> clients = new ArrayList<Socket>();

	public void run() {
			isGoing = true;
			while (isGoing) {
				List<Socket> current = clients;
				List<Socket> remove = new ArrayList<Socket>();


				for (int i = 0; i < current.size(); i++) {
					Socket client = current.get(i);
					try {
						if (client.getInputStream() == null) remove.add(client);

						else {
							DataInputStream in = new DataInputStream(
									new BufferedInputStream(client.getInputStream()));

							if (in.available() > 0) {
								String message = in.readUTF();
								DataOutputStream out = new DataOutputStream(client.getOutputStream());
								out.writeUTF(Main.command(client.getInetAddress().toString(), message));
								remove.add(client);
							}
						}
					} catch (Exception e) {
						remove.add(client);
					}
				}


				for (Socket re : remove)
					Server.remove(re);
			}

	}

	public static void send(String ip, String s) throws IOException {
		for (Socket client : ClientThread.clients)
			if (client.getInetAddress().toString().equals(ip)) {
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF(s);
				return;
			}
	}

	public void shutdown() {
		isGoing = false;
	}
}
