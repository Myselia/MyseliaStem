package com.mycelia.stem.communication;

import java.io.IOException;
import java.util.ArrayList;

import com.mycelia.common.constants.Constants.componentType;
import com.mycelia.stem.communication.seekers.ISeek;

public class BroadCaster {

	/*
	 * 0: Daemons 1: Lenses 2: Sandboxes
	 */
	private BroadcastTorch daemonBcast = null;
	private BroadcastTorch lensBcast = null;
	private BroadcastTorch sandboxBcast = null;

	public BroadCaster() {
	}

	public void seekDaemons(ArrayList<ISeek> seekers) {
		if (daemonBcast == null)
			daemonBcast = new BroadcastTorch(componentType.DAEMON, seekers);
		seekComponent(daemonBcast);
	}

	public void seekLenses(ArrayList<ISeek> seekers) {
		if (lensBcast == null)
			lensBcast = new BroadcastTorch(componentType.LENS, seekers);
		seekComponent(lensBcast);
	}

	public void seekSandboxes(ArrayList<ISeek> seekers) {
		if (sandboxBcast == null)
			sandboxBcast = new BroadcastTorch(componentType.SANDBOX, seekers);
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
