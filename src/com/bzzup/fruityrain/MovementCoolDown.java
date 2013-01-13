package com.bzzup.fruityrain;

import java.util.Timer;
import java.util.TimerTask;

public class MovementCoolDown {
	private boolean valid;
	private Timer timer;
	private long delay = 1000;

	// private static CoolDown instance = null;

	public MovementCoolDown(long delay) {
		timer = new Timer();
		valid = true;
		this.delay = delay;
	}

	public boolean checkValidity() {
		if (valid) {
			valid = false;
			timer.schedule(new Task(), delay);
			return true;
		}
		return false;
	}

	class Task extends TimerTask {

		public void run() {
			valid = true;
		}

	}
}
