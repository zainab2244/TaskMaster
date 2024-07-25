package backend.taskMaster.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventService {
    private List<Event> eventList;

    public EventService() {
        this.eventList = new ArrayList<>();
    }

    public Event createEvent(Scanner scanner, Scheduling scheduling) {
        Event newEvent = null;
        try {
            System.out.println("Creating a new event...");

            System.out.print("Enter event name: ");
            String nameOfEvent = scanner.nextLine();

            System.out.print("Enter event date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateInput);

            System.out.print("Enter event category: ");
            String category = scanner.nextLine();

            System.out.print("Enter event start time (HH:MM): ");
            String startTimeInput = scanner.nextLine();
            LocalTime startTime = LocalTime.parse(startTimeInput);

            System.out.print("Enter event end time (HH:MM): ");
            String endTimeInput = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(endTimeInput);

            System.out.print("Does this event repeat? (yes/no): ");
            String repeatChoice = scanner.nextLine();
            boolean isRepeating = repeatChoice.equalsIgnoreCase("yes");

            String repeatInterval = "";
            LocalDate repeatEndDate = null;
            if (isRepeating) {
                System.out.print("Enter repeat interval (day/week/month/year): ");
                repeatInterval = scanner.nextLine();

                System.out.print("Enter repeat end date (YYYY-MM-DD): ");
                String repeatEndInput = scanner.nextLine();
                repeatEndDate = LocalDate.parse(repeatEndInput);
            }

            newEvent = new Event(nameOfEvent, date, category, startTime, endTime, repeatInterval, repeatEndDate,
                    isRepeating);

            if (newEvent.canBeScheduled(eventList)) {
                eventList.add(newEvent);
                System.out.println("Event created successfully!");
            } else {
                System.out.println("Event cannot be scheduled due to conflicts");
                newEvent = null; // only if event can't be scheduled
            }
        } catch (Exception e) {
            System.out.println("Error occurred while creating event: " + e.getMessage());
        }
        return newEvent;
    }

    public List<Event> getEvents() {
        return eventList;
    }

    public void updateEvent(String eventName, Event updatedEvent) {
        for (int i = 0; i < eventList.size(); i++) {
            Event existingEvent = eventList.get(i);
            if (existingEvent.getNameOfEvent().equalsIgnoreCase(eventName)) {
                eventList.set(i, updatedEvent);
                return;
            }
        }
        System.out.println("Event " + eventName + " not found.");
    }
}
