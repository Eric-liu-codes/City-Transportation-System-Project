package com.solvd.citytransportationsystemproject.models;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Taxi")
@XmlRootElement(name = "Taxi")
@XmlAccessorType(XmlAccessType.FIELD)

public class Taxi extends Vehicle {
	
	@JsonProperty("licensePlate")
    @XmlElement
    private String licensePlate;
	
	@JsonProperty("vehicleId")
    @XmlElement
    private long vehicleId;
    
    public Taxi() {
		super();
	}

	public Taxi(long id, String make, String model, Integer year, Integer capacity, long driverId, String licensePlate, long vehicleId) {
        super(id, make, model, year, capacity, driverId);
        this.licensePlate = licensePlate;
        this.vehicleId = vehicleId;
    }

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Taxi))
			return false;
		Taxi taxi = (Taxi) obj;
		return super.equals(taxi) &&
				Objects.equals(licensePlate, taxi.licensePlate) &&
				vehicleId == taxi.vehicleId;
	}


}
