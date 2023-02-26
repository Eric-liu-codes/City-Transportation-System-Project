package com.solvd.citytransportationsystemproject.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Vehicle")
@XmlRootElement(name = "Vehicle")
@XmlAccessorType(XmlAccessType.FIELD)

public class Vehicle extends Model {
	
	@JsonProperty("make")
    @XmlElement
    private String make;
	
	@JsonProperty("model")
    @XmlElement
    private String model;
	
	@JsonProperty("year")
    @XmlElement
    private Integer year;
	
	@JsonProperty("capacity")
    @XmlElement
    private Integer capacity;
	
	@JsonProperty("driverID")
    @XmlElement
    private long driverId;
    
    public Vehicle() {
		super();
	}

	public Vehicle(long id, String make, String model, Integer year, Integer capacity, long driverId) {
        super(id);
        this.make = make;
        this.model = model;
        this.year = year;
        this.capacity = capacity;
        this.driverId = driverId;
    }

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}
    
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Vehicle)) {
			return false;
		}
		Vehicle vehicle = (Vehicle) o;
		return this.getId() == vehicle.getId() &&
				this.make.equals(vehicle.getMake()) &&
				this.model.equals(vehicle.getModel()) &&
				this.year == vehicle.getYear() &&
				this.capacity == vehicle.getCapacity();
	}
    
}
