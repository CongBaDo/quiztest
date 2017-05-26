package nemo.com.androidquiz.model.seach;

/**
 * Created by doba on 5/26/17.
 */

public class SearchingReqObj {

    private String term;
    private String location;
    private double latitude;
    private double longitude;
    private int radius;
    private int limit;


    public SearchingReqObj(){

    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTerm() {
        return term;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRadius() {
        return radius;
    }

    public int getLimit() {
        return limit;
    }
}
