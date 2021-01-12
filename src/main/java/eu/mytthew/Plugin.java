package eu.mytthew;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Plugin {
	private final EventManager eventManager = new EventManager();

	public static void main(String[] args) {

	}

	public Plugin() throws GeneralSecurityException, IOException {
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
