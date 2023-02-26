package com.solvd.citytransportationsystemproject.models;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Station")
@XmlRootElement(name = "Station")
@XmlAccessorType(XmlAccessType.FIELD)
public class Station extends Model {

    @JsonProperty("name")
    @XmlElement
    private String name;

    @JsonProperty("type")
    @XmlElement
    private String type;

    @JsonProperty("address")
    @XmlElement
    private String address;

    @JsonProperty("routeId")
    @XmlElement
    private long routeId;
    
    @JsonProperty("neighbors")
    @XmlElement(name = "neighbor")
    private List<Station> neighbors;

    private Integer index;

    public Station() {
        super();
    }

    public Station(long id, String name, String type, String address, long routeId, List<Station> neighbors) {
        super(id);
        this.name = name;
        this.type = type;
        this.address = address;
        this.routeId = routeId;
        this.neighbors = neighbors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public List<Station> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Station> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Station))
            return false;
        Station station = (Station) obj;
        return Objects.equals(name, station.name) && Objects.equals(type, station.type)
                && Objects.equals(address, station.address) && routeId == station.routeId;
    }

    public boolean isConnected(Station station2) {
        for (Station neighbor : neighbors) {
            if (neighbor.equals(station2)) {
                return true;
            }
        }
        return false;
    }

    public int getDistance(StationConnection stationConnection) {
        if (stationConnection.getStation1Id() == getId() || stationConnection.getStation2Id() == getId()) {
            return stationConnection.getDistance();
        }
        return -1;
    }


	public void addStationNeighbor(StationConnection stationConnection) {
	    neighbors.add(stationConnection.getOtherStation(this));
	}



}

