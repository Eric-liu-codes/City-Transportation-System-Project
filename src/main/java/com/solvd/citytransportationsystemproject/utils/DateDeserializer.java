package com.solvd.citytransportationsystemproject.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateDeserializer extends StdDeserializer<Date> {
	
    private final Logger LOGGER = LogManager.getLogger(DateDeserializer.class);

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public DateDeserializer() {
		this(null);
	}

	public DateDeserializer(Class<Date> t) {
		super(t);
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String date = parser.getText();
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			LOGGER.info(e);
		}
		return null;
	}
}
