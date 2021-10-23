package com.example.airlinesManagment;

import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Destination {

    @Id 
    @GeneratedValue
    private Long dest_id;

    private String name;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride( name = "altitude", column = @Column(name = "location_altitude")),
        @AttributeOverride( name = "longitude", column = @Column(name = "location_longitude"))
      })
    private Location location;

    public Destination(){}
    
    public Destination(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Long getDest_id() {
        return dest_id;
    }

    public void setDest_id(Long dest_id) {
        this.dest_id = dest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Destination [dest_id=" + dest_id + ", location=" + location + ", name=" + name + "]";
    }

    public double getDistanceTo(Location location)
    {
        return this.location.getDistanceTo(location);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dest_id == null) ? 0 : dest_id.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Destination other = (Destination) obj;
        if (dest_id == null) {
            if (other.dest_id != null)
                return false;
        } else if (!dest_id.equals(other.dest_id))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
