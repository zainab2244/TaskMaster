package backend.taskMaster.model;

import java.time.LocalTime;

public class Settings {
    private int workHoursThreshold;
    private LocalTime startTime;
    private LocalTime endTime;
    private int breakDuration;

    public Settings(int workHoursThreshold, LocalTime startTime, LocalTime endTime, int breakDuration) {
        this.workHoursThreshold = workHoursThreshold;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakDuration = breakDuration;
    }

    public int getWorkHoursThreshold() {
        return workHoursThreshold;
    }

    public void setWorkHoursThreshold(int workHoursThreshold) {
        this.workHoursThreshold = workHoursThreshold;
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

    public int getBreakDuration() {
        return breakDuration;
    }

    public void setBreakDuration(int breakDuration) {
        this.breakDuration = breakDuration;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "workHoursThreshold=" + workHoursThreshold +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", breakDuration=" + breakDuration +
                '}';
    }
}