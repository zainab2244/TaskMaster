package backend.taskMaster.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Event {
    private String nameOfEvent;
    private LocalDate date;
    private String category;
    private LocalTime startTime;
    private LocalTime endTime;
    private String repeatInterval;
    private LocalDate repeatEndDate;
    private boolean isRepeating;
    private Duration totalTime;

    public Event(String nameOfEvent, LocalDate date, String category, LocalTime startTime, LocalTime endTime,
            String repeatInterval, LocalDate repeatEndDate, boolean isRepeating) {
        this.nameOfEvent = nameOfEvent;
        this.date = date;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatInterval = repeatInterval;
        this.repeatEndDate = repeatEndDate;
        this.isRepeating = isRepeating;
        calculateTotalTime(); // Calculate total time upon initialization?
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public LocalDate getRepeatEndDate() {
        return repeatEndDate;
    }

    public void setRepeatEndDate(LocalDate repeatEndDate) {
        this.repeatEndDate = repeatEndDate;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    public Duration getTotalTime() {
        return totalTime;
    }

    // Calculate total time
    private void calculateTotalTime() {
        if (startTime != null && endTime != null) {
            this.totalTime = Duration.between(startTime, endTime);
        } else {
            this.totalTime = Duration.ZERO; // Duration object needed
        }
    }

    public Event getEvent() {
        return this;
    }

    public LocalDate getEventDate() {
        return date;
    }

    public void updateEvent(Event updatedEvent) {
        this.nameOfEvent = updatedEvent.getNameOfEvent();
        this.date = updatedEvent.getDate();
        this.category = updatedEvent.getCategory();
        this.startTime = updatedEvent.getStartTime();
        this.endTime = updatedEvent.getEndTime();
        this.repeatInterval = updatedEvent.getRepeatInterval();
        this.repeatEndDate = updatedEvent.getRepeatEndDate();
        this.isRepeating = updatedEvent.isRepeating();
        calculateTotalTime(); // Recalculate total time?
    }

    public boolean canBeScheduled(List<Event> scheduledEvents) {
        // Check same date same time conflicts
        for (Event scheduledEvent : scheduledEvents) {
            if (this.date.equals(scheduledEvent.getDate())) {
                if (this.startTime.isBefore(scheduledEvent.getEndTime()) &&
                        this.endTime.isAfter(scheduledEvent.getStartTime())) {
                    return false;
                }
            }
        }

        // Check repeating interval conflicts
        if (isRepeating) {
            // Go through scheduled events
            for (Event scheduledEvent : scheduledEvents) {
                if (scheduledEvent.isRepeating()) {
                    // Make temp date to keep track of repititon
                    LocalDate tempDate = scheduledEvent.getDate();
                    while (tempDate.isBefore(scheduledEvent.getRepeatEndDate())
                            || tempDate.equals(scheduledEvent.getRepeatEndDate())) {
                        // If temp date (any repeting interval date) conflicts with scheduled event date
                        // and they are conflicting times return false
                        if ((tempDate.equals(this.date) || this.date.equals(tempDate)) &&
                                this.startTime.isBefore(scheduledEvent.getEndTime()) &&
                                this.endTime.isAfter(scheduledEvent.getStartTime())) {
                            return false;
                        }

                        // Increment temp date based on interval
                        switch (scheduledEvent.getRepeatInterval().toLowerCase()) {
                            case "day":
                                tempDate = tempDate.plus(1, ChronoUnit.DAYS);
                                break;
                            case "week":
                                tempDate = tempDate.plus(1, ChronoUnit.WEEKS);
                                break;
                            case "month":
                                tempDate = tempDate.plus(1, ChronoUnit.MONTHS);
                                break;
                            case "year":
                                tempDate = tempDate.plus(1, ChronoUnit.YEARS);
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Invalid repeat interval: " + scheduledEvent.getRepeatInterval());
                        }
                    }
                }
            }
        }

        // Check if this event conflicts with any scheduled repeating events (if
        // this.event isn't repeating now)
        if (!repeatInterval.isEmpty() && repeatEndDate != null) {
            LocalDate tempDate = this.date;
            while (tempDate.isBefore(repeatEndDate) || tempDate.equals(repeatEndDate)) {
                for (Event scheduledEvent : scheduledEvents) {
                    if ((tempDate.equals(scheduledEvent.getDate()) || this.date.equals(scheduledEvent.getDate())) &&
                            this.startTime.isBefore(scheduledEvent.getEndTime()) &&
                            this.endTime.isAfter(scheduledEvent.getStartTime())) {
                        return false;
                    }
                }
                switch (this.repeatInterval.toLowerCase()) {
                    case "day":
                        tempDate = tempDate.plus(1, ChronoUnit.DAYS);
                        break;
                    case "week":
                        tempDate = tempDate.plus(1, ChronoUnit.WEEKS);
                        break;
                    case "month":
                        tempDate = tempDate.plus(1, ChronoUnit.MONTHS);
                        break;
                    case "year":
                        tempDate = tempDate.plus(1, ChronoUnit.YEARS);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid repeat interval: " + this.repeatInterval);
                }
            }
        }

        return true; // if can be scheduled
    }

    @Override
    public String toString() {
        Duration totalTime = getTotalTime();
        long hours = totalTime.toHours();
        long minutes = totalTime.toMinutes() % 60; // Minutes remainder

        return "Event{" +
                "nameOfEvent='" + nameOfEvent + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", repeatInterval='" + repeatInterval + '\'' +
                ", repeatEndDate=" + repeatEndDate +
                ", isRepeating=" + isRepeating +
                ", totalTime=" + hours + " hours " + minutes + " minutes" +
                '}';
    }
}
