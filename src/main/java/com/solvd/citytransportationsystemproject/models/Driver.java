package com.solvd.citytransportationsystemproject.models;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Driver")
@XmlRootElement(name = "Driver")
@XmlAccessorType(XmlAccessType.FIELD)

public class Driver extends Person{

	@JsonProperty("liscenseNumber")
    @XmlElement
	private String licenseNumber;
	
	@JsonProperty("yearsOfExperience")
    @XmlElement
    private Integer yearsOfExperience;
	
	@JsonProperty("personID")
    @XmlElement
    private long personId;
    
    public Driver() {
		super();
	}

	public Driver(long id, String firstName, String lastName, String address, String phoneNumber, String email, String licenseNumber, Integer yearsOfExperience, long personId) {
        super(id, firstName, lastName, address, phoneNumber, email);
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.personId = personId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Driver))
            return false;
        if (!super.equals(o))
            return false;
        Driver driver = (Driver) o;
        return yearsOfExperience == driver.yearsOfExperience &&
                personId == driver.personId &&
                Objects.equals(licenseNumber, driver.licenseNumber);
    }

}

