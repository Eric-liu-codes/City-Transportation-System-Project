package com.solvd.citytransportationsystemproject.models;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Person")
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)

public class Person extends Model{
	
	@JsonProperty("firstName")
    @XmlElement
    private String firstName;
	
	@JsonProperty("lastName")
    @XmlElement
    private String lastName;
	
	@JsonProperty("address")
    @XmlElement
    private String address;
	
	@JsonProperty("phoneNumber")
    @XmlElement
    private String phoneNumber;
	
	@JsonProperty("email")
    @XmlElement
    private String email;
    
    
    public Person() {
		super();
	}

	public Person(long id, String firstName, String lastName, String address, String phoneNumber, String email) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Person))
            return false;
        Person person = (Person) obj;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(address, person.address) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(email, person.email);
    }


}