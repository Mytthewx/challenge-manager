package eu.mytthew;

import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
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
				String todayDate = LocalDate.now().toString();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("description", description);
				if (fileOperation.fileExist("last")) {
					jsonObject = fileOperation.openFile("last");
					if (!jsonObject.getString("date").equals(todayDate)) {
						description = jsonObject.getInt("description");
						fileOperation.deleteFile("last");
						eventManager.addEvent(description + 1);
						jsonObject.put("date", todayDate);
						fileOperation.createFile("last", jsonObject);
					}
				} else {
					eventManager.addEvent(description);
					jsonObject.put("date", todayDate);
					fileOperation.createFile("last", jsonObject);
				}
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}, 20, TimeUnit.SECONDS);
	}

}
