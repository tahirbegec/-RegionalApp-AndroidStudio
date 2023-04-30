package de.uni_marburg.sp21.Model;

public class TimeInterval {
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String start;
    private String end;

    public TimeInterval(String start, String end) {
        startHour = Integer.parseInt(start.split(":")[0]);
        startMinute = Integer.parseInt(start.split(":")[1]);
        endHour = Integer.parseInt(end.split(":")[0]);
        endMinute = Integer.parseInt(end.split(":")[1]);
        this.start = start;
        this.end = end;
    }

    public boolean isInbetween(int hour, int minute) {
        if(hour >= startHour && hour <= endHour) {
            if(hour == startHour) {
                if(minute < startMinute) {
                    return false;
                }
            }
            if(hour == endHour) {
                if(minute > endMinute) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return start + " - " + end;
    }
}