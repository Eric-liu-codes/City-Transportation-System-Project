package com.solvd.citytransportationsystemproject.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {
	final Logger LOGGER = LogManager.getLogger(TimestampDeserializer.class.getName());

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Timestamp deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String date = parser.getText();
		try {
			Date parsedDate = dateFormat.parse(date);
			return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			LOGGER.info(e);
		}
		return null;
	}
}
