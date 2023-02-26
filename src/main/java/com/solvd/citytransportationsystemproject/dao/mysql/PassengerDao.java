package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IPassengerDao;
import com.solvd.citytransportationsystemproject.models.Passenger;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PassengerDao extends MySQLDao<Passenger> implements IPassengerDao {
	private static final Logger LOGGER = LogManager.getLogger(PassengerDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Passenger (id, number_of_rides, person_id) VALUES (?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Passenger WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Passenger SET number_of_rides = ?, person_id = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Passenger WHERE id = ?";
	private static final String GET_ENTITY_BY_PERSON_ID_SQL = "SELECT * FROM Passenger WHERE person_id = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Passenger";

	@Override
	public Passenger createEntity(Passenger entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setInt(2, entity.getNumberOfRides());
			statement.setLong(3, entity.getPersonId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return entity;
	}

	@Override
	public Passenger getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Passenger passenger = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			passenger = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return passenger;
	}

	@Override
	public void updateEntity(Passenger entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setInt(1, entity.getNumberOfRides());
			statement.setLong(2, entity.getPersonId());
			statement.setLong(3, entity.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
	}

	@Override
	public void deleteEntity(long id) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(DELETE_ENTITY_SQL);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
	}

	public Passenger getPassengerByPersonId(long personId) {
		PreparedStatement statement = null;
		Connection connection = null;
		Passenger passenger = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_PERSON_ID_SQL);
			statement.setLong(1, personId);
			resultSet = statement.executeQuery();
			passenger = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return passenger;
	}

	@Override
	public List<Passenger> getAllPassengers() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Passenger> passengers = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Passenger passenger = new Passenger();
				passenger.setId(resultSet.getLong("id"));
				passenger.setNumberOfRides(resultSet.getInt("number_of_rides"));
				passenger.setPersonId(resultSet.getLong("person_id"));
				passengers.add(passenger);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return passengers;
	}

	@Override
	protected Passenger resultSetToObject(ResultSet resultSet) {
		Passenger passenger = null;
		try {
			while (resultSet.next()) {
				passenger = new Passenger();
				passenger.setId(resultSet.getLong("id"));
				passenger.setNumberOfRides(resultSet.getInt("number_of_rides"));
				passenger.setPersonId(resultSet.getLong("person_id"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return passenger;
	}

}