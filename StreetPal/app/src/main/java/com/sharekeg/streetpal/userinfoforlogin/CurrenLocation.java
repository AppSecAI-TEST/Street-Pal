package com.sharekeg.streetpal.userinfoforlogin;

/**
 * Created by HP on 12/04/17.
 */

public class CurrenLocation {
    private String Lat;
    private String Lon;

    public CurrenLocation(double lat, double lon) {
    }


    public String getLat() {
        return Lat;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
    }
 public String getLon() {
        return Lon;
    }

    public void setLon(String Lon) {
        this.Lon = Lon;
    }

}
