import java.util.Timer; // This is a Java utility class that allows us to schedule a task to run at a future point in time or repeatedly at fixed intervals.
import java.util.TimerTask; // This is a Java utility task that can be scheduled for one-time or repeated execution by a Timer.
import java.time.LocalTime;

public class Reminder {
    private Timer timer; // Timer object to schedule when the timer task should be executed
    private long workInterval; // Duration of work period in milliseconds
    private long breakInterval; // Duration of break period in milliseconds
    private LocalTime startTime; // The time of day when work periods start, pulled from user settings
    private LocalTime endTime; // The time of day when work periods end, pulled from user settings

    // Constructor to initialize the Reminder object with the work interval, break interval, start time, and end time
    public Reminder(long workInterval, long breakInterval, LocalTime startTime, LocalTime endTime) {
        this.workInterval = workInterval; // Set the work interval
        this.breakInterval = breakInterval; // Set the break interval
        this.startTime = startTime; // Set the start time from user settings
        this.endTime = endTime; // Set the end time from user settings
        timer = new Timer(); // Create a new Timer object
    }

    // Method to start the reminder scheduling based on the current (now) time
    public void start() { // Initiates the reminder scheduling based on the current time. It determines whether the current time is before, within, or after the designated working hours and schedules the timer accordingly. 
        LocalTime now = LocalTime.now(); // Get the current local time and store it in the variable "now"
        if (now.isBefore(startTime)) {
            long delay = java.time.Duration.between(now, startTime).toMillis(); // If current time is before start time, calculate the delay until the start time
            timer.schedule(new StartWorkTask(), delay);  // Schedule the StartWorkTask to start after the calculated delay
        } else if (now.isAfter(startTime) && now.isBefore(endTime)) { // If the current time is within working hours then...
            startWorkPeriod(); // ... start the work period immediately
        } else {
            System.out.println("Current time is outside of working hours."); // If the time is outside of set working hours, this will display.
        }
    }

    // Method to start a work period by scheduling the WorkTask
    private void startWorkPeriod() { // Schedules the WorkTask to start after the work interval.
        timer.schedule(new WorkTask(), workInterval); // Schedules the WorkTask to execute after the specified workInterval in milliseconds. This task will handle the transition from work to break periods.
    }

    // Inner class to start the work period task
    class StartWorkTask extends TimerTask {
        @Override
        public void run() {
            startWorkPeriod(); // When this task runs, it starts the work period at the designated start time
            // run() Method: Calls startWorkPeriod() when executed. This method is scheduled to run after the computed delay when starting the work period.
        }
    }

    // Inner class to manage work tasks and breaks
    class WorkTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Time to take a 15-minute break!"); // When this task runs, it announces the start of a break
            showBreakTimer(); // Start the break timer and show break timer when run
        }

        // Show break timer and schedule next work period
        private void showBreakTimer() { // Method to show and manage the break timer, creates a new Timer for managing the break countdown
            Timer breakTimer = new Timer(); // Create a new timer for the break period
            TimerTask task = new TimerTask() {
                int remainingSeconds = (int) (breakInterval / 1000); // Convert break interval to seconds

                @Override
                public void run() {
                    // Countdown Logic:
                    // Checks if remainingSeconds is greater than 0. If it is, it prints the remaining break time and decrements remainingSeconds.
                    // If remainingSeconds is 0, it prints a message indicating the end of the break, cancels the break timer, and schedules a new WorkTask if within working hours.
                    if (remainingSeconds > 0) {
                        System.out.println("Break time remaining: " + formatTime(remainingSeconds)); // If there are remaining seconds, print the remaining time
                        remainingSeconds--; // Decrement the remaining seconds
                    } else {
                        System.out.println("Break is over. Back to work!"); // If no remaining seconds, announce the end of the break
                        breakTimer.cancel(); // Cancel the break timer
                        LocalTime now = LocalTime.now(); // Get the current local time
                        if (now.isBefore(endTime)) {
                            timer.schedule(new WorkTask(), workInterval); // If the current time is before endTime, it schedules a new WorkTask after the workInterval (it is scheduling the next work period)
                            // This creates a loop where after each work period, a break period is scheduled, and after each break period, a new work period is scheduled, as long as the current time is within the working hours. The work period is set to 2 hours, which follows a 15 minute break after.
                            // The implicit loop is formed by the WorkTask scheduling itself at the end of each break period. This creates a continuous cycle of work and break periods within the specified working hours. The loop is implicit because it is achieved through the repeated scheduling of tasks rather than a traditional loop structure such as "for" or "while".
                        } else {
                            System.out.println("End of working hours."); // If the current time is after the end time, announce the end of working hours
                        }
                    }
                }
            };
            breakTimer.scheduleAtFixedRate(task, 0, 1000); // Schedules the countdown task to run every second
        }

        // Method to format the remaining time in minutes and seconds
        private String formatTime(int seconds) { // Formats the remaining seconds of the break into a string with minutes and seconds
            int minutes = seconds / 60; // Calculate the minutes
            int remainingSeconds = seconds % 60; // Calculate the remaining seconds
            return String.format("%02d:%02d", minutes, remainingSeconds); // // Return the formatted time string
        }
    }
}
