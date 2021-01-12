package eu.mytthew;

import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Plugin {
	private final EventManager eventManager = new EventManager();

	public Plugin() throws GeneralSecurityException, IOException {
		FileOperation fileOperation = new FileOperation();
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(() -> {
			try {
				int description = 1;
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("description", description);
				if (fileOperation.fileExist("last")) {
					jsonObject = fileOperation.openFile("last");
					description = jsonObject.getInt("description");
					description = description + 1;
					fileOperation.deleteFile("last");
				}
				eventManager.addEvent(description);
				fileOperation.createFile("last", jsonObject);

			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}, 20, TimeUnit.SECONDS);
	}

}
