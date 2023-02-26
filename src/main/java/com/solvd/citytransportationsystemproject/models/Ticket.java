package com.solvd.citytransportationsystemproject.models;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Ticket")
@XmlRootElement(name = "Ticket")
@XmlAccessorType(XmlAccessType.FIELD)

public class Ticket extends Model {
	
	@JsonProperty("paymentMethod")
    @XmlElement
    private String paymentMethod;
	
	@JsonProperty("fare")
    @XmlElement
    private float fare;
	
	@JsonProperty("passengerId")
    @XmlElement
    private long passengerId;
    
    public Ticket() {
		super();
	}

	public Ticket(long id, String paymentMethod, float fare, long passengerId) {
        super(id);
        this.paymentMethod = paymentMethod;
        this.fare = fare;
        this.passengerId = passengerId;
    }

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public float getFare() {
		return fare;
	}

	public void setFare(float fare) {
		this.fare = fare;
	}

	public long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Ticket))
			return false;
		Ticket ticket = (Ticket) obj;
		return Float.compare(ticket.fare, fare) == 0 &&
				passengerId == ticket.passengerId &&
				Objects.equals(paymentMethod, ticket.paymentMethod);
	}


}
