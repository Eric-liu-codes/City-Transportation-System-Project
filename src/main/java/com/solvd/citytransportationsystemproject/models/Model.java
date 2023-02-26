package com.solvd.citytransportationsystemproject.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Model")
@XmlRootElement(name = "Model")
@XmlAccessorType(XmlAccessType.FIELD)

abstract public class Model {
	
	@JsonProperty("id")
    @XmlElement
	private long id;
	
	public Model() {
		super();
	}
	
	public Model(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
