/****************************************************************
 *  Copyright (C) Solar Client, PlumpOrange - All Rights Reserved
 * Unauthorized copying, distribution, or sharing of this file or code, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by PlumpOrange <@PlumpOrange#1604>, 2019
 ****************************************************************/
package com.orange.plump.Solar;

import net.minecraft.client.Minecraft;

public class ClientThread extends Thread {

	private boolean isRunning = false;
	private static float tpm = 4f;

	public void run() {
		isRunning = true;
		while(isRunning) {
			Minecraft.doNovaUpdate();
			try {
				this.sleep((long)(60000 / tpm));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	public void shutdown() {
		isRunning = false;
	}
	
}
