package de.uni_marburg.sp21.Model;

public class Location {

    private double lat;
    private double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**
     * calculates the disctance between the two locations returns true if the difference is lower
     * @param location location for calculation
     * @param distanceInM distance which the two location should not be greater than
     * @return true if distance is lower or equal, false if distance is greater than given distance
     */
    public boolean distanceSmallerThan(Location location, int distanceInM) {
        float[] result = new float[1];
        android.location.Location.distanceBetween(this.lat, this.lon, location.getLat(), location.getLon(), result);
        if(result[0] <= distanceInM) {
            return true;
        } else {
            return false;
        }
    }
}
