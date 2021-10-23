package com.example.airlinesManagment;

import javax.persistence.Embeddable;
import org.apache.lucene.spatial.util.GeoDistanceUtils;

@Embeddable
public class Location {
    
    private Double longitude;
    private Double altitude;

    Location(){}

    Location(Double longitude, Double altitude){
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "Location [altitude=" + altitude + ", longitude=" + longitude + "]";
    }    

    public double getDistanceTo(Location location){
        final int KM = 1000;
        return GeoDistanceUtils.haversin(this.getAltitude(), this.getLongitude(), 
                                        location.getAltitude(), location.getLongitude()) / KM;
    }
}
