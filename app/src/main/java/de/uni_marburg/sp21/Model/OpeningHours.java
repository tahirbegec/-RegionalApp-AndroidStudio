package de.uni_marburg.sp21.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpeningHours {
    private List<TimeInterval>[] openingHours = new List[7];
    private List<TimeInterval> monday = new ArrayList<>();
    private List<TimeInterval> tuesday = new ArrayList<>();
    private List<TimeInterval> wednesday = new ArrayList<>();
    private List<TimeInterval> thursday = new ArrayList<>();
    private List<TimeInterval> friday = new ArrayList<>();
    private List<TimeInterval> saturday = new ArrayList<>();
    private List<TimeInterval> sunday = new ArrayList<>();

    public OpeningHours(Map<String, List< Map<String, String>>> openingHoursMap) {
        if(openingHoursMap.get("monday") != null) {
            for (Map<String, String> map : openingHoursMap.get("monday")) {
                this.monday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("tuesday") != null) {
            for (Map<String, String> map : openingHoursMap.get("tuesday")) {
                this.tuesday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("wednesday") != null) {
            for (Map<String, String> map : openingHoursMap.get("wednesday")) {
                this.wednesday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("thursday") != null) {
            for (Map<String, String> map : openingHoursMap.get("thursday")) {
                this.thursday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("friday") != null) {
            for (Map<String, String> map : openingHoursMap.get("friday")) {
                this.friday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("saturday") != null) {
            for (Map<String, String> map : openingHoursMap.get("saturday")) {
                this.saturday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        if(openingHoursMap.get("sunday") != null) {
            for (Map<String, String> map : openingHoursMap.get("sunday")) {
                this.sunday.add(new TimeInterval(map.get("start"), map.get("end")));
            }
        }
        openingHours[0] = monday;
        openingHours[1] = tuesday;
        openingHours[2] = wednesday;
        openingHours[3] = thursday;
        openingHours[4] = friday;
        openingHours[5] = saturday;
        openingHours[6] = sunday;
    }

    public List<TimeInterval>[] getOpeningHoursArray() {
        return openingHours;
    }
}
