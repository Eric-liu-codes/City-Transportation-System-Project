package com.solvd.citytransportationsystemproject.utils;

import java.io.FileInputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.solvd.citytransportationsystemproject.models.Bus;
import com.solvd.citytransportationsystemproject.models.Passenger;
import com.solvd.citytransportationsystemproject.models.Route;
import com.solvd.citytransportationsystemproject.models.Station;
import com.solvd.citytransportationsystemproject.models.StationConnection;
import com.solvd.citytransportationsystemproject.models.Taxi;
import com.solvd.citytransportationsystemproject.models.Train;
import com.solvd.citytransportationsystemproject.models.VehicleMaintenance;

public class Parser {

	final private static Logger LOGGER = LogManager.getLogger(Parser.class.getName());

	public List<Station> parseXML(String filePath) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		List<Station> stations = new ArrayList<>();

		try {
			XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));
			Station station = null;
			String name = null;
			String type = null;
			String address = null;
			long routeId = 0L;

			while (reader.hasNext()) {
				Integer event = reader.next();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if (reader.getLocalName().equalsIgnoreCase("station")) {
						station = new Station();
					} else if (reader.getLocalName().equalsIgnoreCase("name")) {
						name = "";
					} else if (reader.getLocalName().equalsIgnoreCase("type")) {
						type = "";
					} else if (reader.getLocalName().equalsIgnoreCase("address")) {
						address = "";
					} else if (reader.getLocalName().equalsIgnoreCase("routeId")) {
						routeId = Long.parseLong(reader.getAttributeValue(null, "id"));
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					String data = reader.getText().trim();
					if (name != null) {
						name += data;
					} else if (type != null) {
						type += data;
					} else if (address != null) {
						address += data;
					} else if (routeId != 0L) {
						routeId = Long.parseLong(data);
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					if (reader.getLocalName().equalsIgnoreCase("station")) {
						station.setName(name);
						station.setType(type);
						station.setAddress(address);
						station.setRouteId(routeId);
						// Do something with the completed Station object
						stations.add(station);
						station = null;
					} else if (reader.getLocalName().equalsIgnoreCase("name")) {
						name = null;
					} else if (reader.getLocalName().equalsIgnoreCase("type")) {
						type = null;
					} else if (reader.getLocalName().equalsIgnoreCase("address")) {
						address = null;
					} else if (reader.getLocalName().equalsIgnoreCase("routeId")) {
						routeId = 0L;
					}
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		try {
			factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("stationNeighbor.xml"));

			List<StationConnection> stationConnections = new ArrayList<>();
			long id = 0L;
			long station1Id = 0L;
			long station2Id = 0L;
			int distance = 0;

			while (reader.hasNext()) {
				Integer event = reader.next();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if (reader.getLocalName().equalsIgnoreCase("stationNeighbor")) {
						station1Id = Long.parseLong(reader.getAttributeValue(null, "station1Id"));
						station2Id = Long.parseLong(reader.getAttributeValue(null, "station2Id"));
					} else if (reader.getLocalName().equalsIgnoreCase("distance")) {
						distance = Integer.parseInt(reader.getElementText());
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					if (reader.getLocalName().equalsIgnoreCase("stationNeighbor")) {
						StationConnection stationConnection = new StationConnection(id, station1Id, station2Id, distance);

						for (Station s : stations) {
							if (s.getId() == station1Id) {
								s.addStationNeighbor(stationConnection);
							}
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return stations;
	}
}
/*
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream(filePath)); Bus bus = null; String busNumber = null; long
 * vehicleID = 0L; while (reader.hasNext()) { Integer event = reader.next();
 * switch (event) { case XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("bus")) { long busId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); bus = new Bus(busId,
 * null, null, 0, 0, 0, 0, 0); } else if
 * (reader.getLocalName().equalsIgnoreCase("busNumber")) { busNumber = ""; }
 * else if (reader.getLocalName().equalsIgnoreCase("vehicleID")) { vehicleID =
 * Long.parseLong(reader.getAttributeValue(null, "id")); } break;
 * 
 * case XMLStreamConstants.CHARACTERS: String data = reader.getText().trim(); if
 * (busNumber != null) { busNumber = data; } break;
 * 
 * case XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("bus")) { // Do something with the
 * completed Bus object bus = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("busNumber")) {
 * bus.setBusNumber(Integer.parseInt(busNumber)); busNumber = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleID")) {
 * bus.setVehicleId(vehicleID); vehicleID = 0L; } break; } } } catch (Exception
 * e) { LOGGER.error(e); }
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("BusMapper.xml")); Bus bus = null; long vehicleId = 0L;
 * Integer busNumber = 0; while (reader.hasNext()) { Integer event =
 * reader.next(); switch (event) { case XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("bus")) { long busId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); bus = new Bus(busId,
 * null, null, 0, 0, 0, 0, 0); } else if
 * (reader.getLocalName().equalsIgnoreCase("busNumber")) { busNumber = 0; } else
 * if (reader.getLocalName().equalsIgnoreCase("vehicleID")) { vehicleId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); } break;
 * 
 * case XMLStreamConstants.CHARACTERS: String data = reader.getText().trim(); if
 * (busNumber != 0) { busNumber = Integer.parseInt(data); } break;
 * 
 * case XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("bus")) { // Do something with the
 * completed Bus object bus = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("busNumber")) {
 * bus.setBusNumber(busNumber); busNumber = 0; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleID")) {
 * bus.setVehicleId(vehicleId); vehicleId = 0L; } break; } } } catch (Exception
 * e) { LOGGER.error(e); }
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("PassengerMapper.xml")); Passenger passenger = null; Integer
 * numberOfRides = 0; long personId = 0L; while (reader.hasNext()) { Integer
 * event = reader.next(); switch (event) { case
 * XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("passenger")) { long passengerId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); passenger = new
 * Passenger(passengerId, null, null, null, null, null, 0, 0); } else if
 * (reader.getLocalName().equalsIgnoreCase("numberOfRides")) { numberOfRides =
 * Integer.parseInt(reader.getAttributeValue(null, "value")); } else if
 * (reader.getLocalName().equalsIgnoreCase("personID")) { personId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); } break;
 * 
 * case XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("passenger")) { // Do something with
 * the completed Passenger object passenger = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("numberOfRides")) {
 * passenger.setNumberOfRides(numberOfRides); numberOfRides = 0; } else if
 * (reader.getLocalName().equalsIgnoreCase("personID")) {
 * passenger.setPersonId(personId); personId = 0L; } break; } } } catch
 * (Exception e) { LOGGER.error(e); }
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("RouteMapper.xml")); Route route = null; String name = null;
 * long vehicleId = 0L; List<Station> stations = new ArrayList<>(); Station
 * station = null;
 * 
 * while (reader.hasNext()) { Integer event = reader.next(); switch (event) {
 * case XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("route")) { long routeId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); route = new
 * Route(routeId, null, 0L, new ArrayList<Station>()); } else if
 * (reader.getLocalName().equalsIgnoreCase("name")) { name = ""; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleId")) { vehicleId =
 * Long.parseLong(reader.getAttributeValue(null, "id")); } else if
 * (reader.getLocalName().equalsIgnoreCase("stations")) { stations = new
 * ArrayList<>(); } else if (reader.getLocalName().equalsIgnoreCase("station"))
 * { station = new Station(); } break;
 * 
 * case XMLStreamConstants.CHARACTERS: String data = reader.getText().trim(); if
 * (name != null) { name += data; } else if (vehicleId != 0L) { vehicleId =
 * Long.parseLong(data); } else if (station != null) { if (station.getName() ==
 * null) { station.setName(data); } else if (station.getType() == null) {
 * station.setType(data); } else if (station.getAddress() == null) {
 * station.setAddress(data); } else if (station.getRouteId() == 0) {
 * station.setRouteId(Long.parseLong(data)); } } break; case
 * XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("route")) { route.setName(name);
 * route.setVehicleId(vehicleId); route.setStations(stations); // Do something
 * with the completed Route object route = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("name")) { name = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleId")) { vehicleId = 0L; }
 * else if (reader.getLocalName().equalsIgnoreCase("stations")) {
 * route.setStations(stations); stations = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("station")) { stations.add(station);
 * station = null; } break; } } } catch (Exception e) { LOGGER.error(e); }
 * 
 * 
 * 
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("TaxiMapper.xml")); Taxi taxi = null; String licensePlate =
 * null; long vehicleId = 0L;
 * 
 * while (reader.hasNext()) { Integer event = reader.next(); switch (event) {
 * case XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("Taxi")) { taxi = new Taxi(); } else
 * if (reader.getLocalName().equalsIgnoreCase("licensePlate")) { licensePlate =
 * ""; } else if (reader.getLocalName().equalsIgnoreCase("vehicleId")) {
 * vehicleId = Long.parseLong(reader.getAttributeValue(null, "id")); } break;
 * 
 * case XMLStreamConstants.CHARACTERS: String data = reader.getText().trim(); if
 * (taxi != null && taxi.getId() == 0) { taxi.setId(Long.parseLong(data)); }
 * else if (licensePlate != null) { licensePlate += data; } else if (vehicleId
 * != 0L) { vehicleId = Long.parseLong(data); } break; case
 * XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("Taxi")) { // Do something with the
 * completed Taxi object taxi = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("licensePlate")) {
 * taxi.setLicensePlate(licensePlate); licensePlate = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleId")) {
 * taxi.setVehicleId(vehicleId); vehicleId = 0L; } break; } } } catch (Exception
 * e) { LOGGER.error(e); }
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("TrainMapper.xml")); Train train = null; String trainHeadcode
 * = null; long vehicleId = 0L; while (reader.hasNext()) { Integer event =
 * reader.next(); switch (event) { case XMLStreamConstants.START_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("Train")) { train = new Train(); }
 * else if (reader.getLocalName().equalsIgnoreCase("trainHeadcode")) {
 * trainHeadcode = ""; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleId")) { vehicleId = 0L; }
 * break;
 * 
 * case XMLStreamConstants.CHARACTERS: String data = reader.getText().trim(); if
 * (train != null && train.getId() == 0) { train.setId(Long.parseLong(data)); }
 * else if (trainHeadcode != null) { trainHeadcode += data; } else if (vehicleId
 * != 0L) { vehicleId = Long.parseLong(data); } break; case
 * XMLStreamConstants.END_ELEMENT: if
 * (reader.getLocalName().equalsIgnoreCase("Train")) { } else if
 * (reader.getLocalName().equalsIgnoreCase("trainHeadcode")) {
 * train.setTrainHeadcode(trainHeadcode); trainHeadcode = null; } else if
 * (reader.getLocalName().equalsIgnoreCase("vehicleId")) {
 * train.setVehicleId(vehicleId); vehicleId = 0L; } break; } } } catch
 * (Exception e) { LOGGER.error(e); }
 * 
 * try { XMLStreamReader reader = factory.createXMLStreamReader(new
 * FileInputStream("VehicleMaintenanceMapper.xml")); VehicleMaintenance
 * vehicleMaintenance = null; Date date = null; String type = null; String
 * description = null; long vehicleId = 0; while (reader.hasNext()) { Integer
 * event = reader.next(); if (event == XMLStreamConstants.START_ELEMENT) {
 * String elementName = reader.getLocalName(); if
 * (elementName.equalsIgnoreCase("VehicleMaintenance")) { vehicleMaintenance =
 * new VehicleMaintenance(); } else if (elementName.equalsIgnoreCase("date")) {
 * date = new Date(0); } else if (elementName.equalsIgnoreCase("type")) { type =
 * ""; } else if (elementName.equalsIgnoreCase("description")) { description =
 * ""; } else if (elementName.equalsIgnoreCase("vehicleId")) { vehicleId = 0; }
 * } else if (event == XMLStreamConstants.CHARACTERS) { String data =
 * reader.getText().trim(); if (vehicleMaintenance != null) { if
 * (vehicleMaintenance.getId() == 0) {
 * vehicleMaintenance.setId(Long.parseLong(data)); } else if (date != null) {
 * try { date = (Date) dateFormat.parse(data); // parse the input data into a
 * Date } catch (ParseException e) { LOGGER.error(e); }
 * vehicleMaintenance.setDate(date); date = null; } else if (type != null) {
 * type += data; vehicleMaintenance.setType(type); } else if (description !=
 * null) { description += data; vehicleMaintenance.setDescription(description);
 * } else if (vehicleId != 0) {
 * vehicleMaintenance.setVehicleId(Long.parseLong(data)); vehicleId = 0; } } }
 * else if (event == XMLStreamConstants.END_ELEMENT) { String elementName =
 * reader.getLocalName(); if
 * (elementName.equalsIgnoreCase("VehicleMaintenance")) {
 * 
 * } else if (elementName.equalsIgnoreCase("date")) { if (date != null) {
 * vehicleMaintenance.setDate(date); date = null; } } else if
 * (elementName.equalsIgnoreCase("type")) { vehicleMaintenance.setType(type);
 * type = null; } else if (elementName.equalsIgnoreCase("description")) {
 * vehicleMaintenance.setDescription(description); description = null; } else if
 * (elementName.equalsIgnoreCase("vehicleId")) {
 * 
 * } } } } catch (Exception e) { LOGGER.error(e); }
 */
