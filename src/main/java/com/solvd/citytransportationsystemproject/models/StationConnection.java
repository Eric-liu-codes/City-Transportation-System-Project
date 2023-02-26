package com.solvd.citytransportationsystemproject.models;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("StationNeighbor")
@XmlRootElement(name = "StationNeighbor")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationConnection extends Model {

    @JsonProperty("station1Id")
    @XmlElement
    private long station1Id;

    @JsonProperty("station2Id")
    @XmlElement
    private long station2Id;

    @JsonProperty("distance")
    @XmlElement
    private int distance;

    public StationConnection() {
        super();
    }

    public StationConnection(long id, long station1Id, long station2Id, int distance) {
        super(id);
        this.station1Id = station1Id;
        this.station2Id = station2Id;
        this.distance = distance;
    }

    public long getStation1Id() {
        return station1Id;
    }

    public void setStation1Id(long station1Id) {
        this.station1Id = station1Id;
    }

    public long getStation2Id() {
        return station2Id;
    }

    public void setStation2Id(long station2Id) {
        this.station2Id = station2Id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof StationConnection))
            return false;
        StationConnection neighbor = (StationConnection) obj;
        return station1Id == neighbor.station1Id && station2Id == neighbor.station2Id && distance == neighbor.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(station1Id, station2Id, distance);
    }

    @Override
    public String toString() {
        return "StationNeighbor [station1Id=" + station1Id + ", station2Id=" + station2Id + ", distance=" + distance + "]";
    }

    public Station getOtherStation(Station station) {
        if (station.getId() == station1Id) {
            return new Station(station2Id, null, null, null, 0L, null);
        } else if (station.getId() == station2Id) {
            return new Station(station1Id, null, null, null, 0L, null);
        } else {
            return null;
        }
    }

}
