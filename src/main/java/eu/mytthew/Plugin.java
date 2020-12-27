package eu.mytthew;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Plugin {
	public static void main(String[] args) {

	}

	public Plugin() {
		EventManager eventManager = new EventManager();
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.schedule(() -> {
			try {
				eventManager.addEvent();
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}, 20, TimeUnit.SECONDS);
	}
}
