package com.bzzup.fruityrain.ship;

import java.util.Timer;
import java.util.TimerTask;

public class ShootingCoolDown {
	private boolean valid;
    private Timer timer;
    private long delay = 500;
//    private static CoolDown instance = null;

    protected ShootingCoolDown(long delay) {
        timer = new Timer();
        valid = true;
        this.delay = delay;
    }

    protected boolean checkValidity() {
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
