package com.mycelia.stem.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mycelia.stem.communication.ComDock.componentType;
import com.mycelia.stem.communication.seekers.ISeek;

public class BroadCaster {

	/*
	 * 0: Daemons 1: Lenses 2: Sandboxes
	 */
	private BroadcastTorch[] broadcastInformation;

	public BroadCaster() {
		broadcastInformation = new BroadcastTorch[3];
	}

	public void seekDaemons(ArrayList<ISeek> seekers) {
		BroadcastTorch daemonBcast = broadcastInformation[0];
		if (daemonBcast == null)
			daemonBcast = new BroadcastTorch(componentType.DAEMON,
					seekers);
		seekComponent(daemonBcast);
	}

	public void seekLenses(ArrayList<ISeek> seekers) {
		BroadcastTorch lensBcast = broadcastInformation[1];
		if (lensBcast == null)
			lensBcast = new BroadcastTorch(componentType.LENS,
					seekers);
		seekComponent(lensBcast);
	}

	public void seekSandboxes(ArrayList<ISeek> seekers) {
		BroadcastTorch sandboxBcast = broadcastInformation[2];
		if (sandboxBcast == null)
			sandboxBcast = new BroadcastTorch(componentType.SANDBOX,
					seekers);
		seekComponent(sandboxBcast);
	}
	

	////////////////////////////////////////////////////////////////////////

	private void seekComponent(BroadcastTorch bcastInfo) {
		Thread t = new Thread(new ComponentSeeker(bcastInfo));
		t.start();
	}

	/*
	 * INTERNAL CLASS
	 */
	class ComponentSeeker implements Runnable {

		private BroadcastTorch bcastTorch;
		private boolean RUNNING = false;

		public ComponentSeeker(BroadcastTorch bcastTorch) {
			System.out.println("THREAD STARTED WITH TYPE: " + bcastTorch.type);
			this.bcastTorch = bcastTorch;
			RUNNING = true;
		}

		@Override
		public void run() {
			bcastTorch.primeBroadcaster();
			
			while (RUNNING) {
				try {
					bcastTorch.seek();
				} catch (InterruptedException e) {
					// If the sleep is interrupted by an attempt to stop the
					// thread, the
					// exception will properly terminate the thread.
					// Do cleanup in here.
					bcastTorch.close();
					Thread.currentThread().interrupt();
				} catch (IOException e) {
					
				}

			}

		}
	}
	/*
	 * ~INTERNAL CLASS
	 */

}
