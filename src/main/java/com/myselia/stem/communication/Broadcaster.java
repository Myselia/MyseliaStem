package com.myselia.stem.communication;

import java.io.IOException;
import java.util.ArrayList;

import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.stem.communication.seekers.Seek;

public class Broadcaster {

	/*
	 * 0: Daemons 1: Lenses 2: Sandboxes
	 */
	private BroadcastTorch daemonBcast = null;
	private BroadcastTorch sandboxBcast = null;

	public Broadcaster() {
	}

	public void seekDaemons(ArrayList<Seek> seekers) {
		if (daemonBcast == null)
			daemonBcast = new BroadcastTorch(ComponentType.DAEMON, seekers);
		seekComponent(daemonBcast);
	}

	public void seekSandboxes(ArrayList<Seek> seekers) {
		if (sandboxBcast == null)
			sandboxBcast = new BroadcastTorch(ComponentType.SANDBOXMASTER, seekers);
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
					Thread.currentThread().interrupt();
				} catch (IOException e) {
					
				}

			}

		}
	}
}
