package eu.mytthew;

import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Plugin {
	private final EventManager eventManager = new EventManager();

	public Plugin() throws GeneralSecurityException, IOException {
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

		exec.schedule(() -> {
			try {
				LocalDate yesterdayDate = LocalDate.now().minusDays(1);
				List<Event> eventList = eventManager.getAllEvents();
				Optional<Event> yesterdayEvent = eventList
						.stream()
						.filter(event -> event.getStart().getDate().toString().equals(yesterdayDate.toString()))
						.findFirst();
				Optional<Event> todayEvent = eventList
						.stream()
						.filter(event -> event.getStart().getDate().toString().equals(LocalDate.now().toString()))
						.findFirst();
				if (todayEvent.isEmpty()) {
					if (yesterdayEvent.isPresent()) {
						String description = yesterdayEvent.get().getDescription();
						int descriptionNumber = Integer.parseInt(description.split("/")[0]);
						eventManager.addEvent(descriptionNumber + 1);
					} else {
						eventManager.addEvent(1);
					}
				}
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}, 20, TimeUnit.SECONDS);
	}

}
