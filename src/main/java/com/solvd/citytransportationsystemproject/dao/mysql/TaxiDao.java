package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.ITaxiDao;
import com.solvd.citytransportationsystemproject.models.Taxi;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaxiDao extends MySQLDao<Taxi> implements ITaxiDao {
	private static final Logger LOGGER = LogManager.getLogger(TaxiDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Taxi (id, vehicle_id, license_plate) VALUES (?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Taxi WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Taxi SET vehicle_id = ?, license_plate = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Taxi WHERE id = ?";
	private static final String GET_ENTITY_BY_VEHICLE_ID_SQL = "SELECT * FROM Taxi WHERE vehicle_id = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Taxi";

	@Override
	public Taxi createEntity(Taxi entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setLong(2, entity.getVehicleId());
			statement.setString(3, entity.getLicensePlate());
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
	public Taxi getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Taxi taxi = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			taxi = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return taxi;
	}

	@Override
	public void updateEntity(Taxi entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setLong(1, entity.getVehicleId());
			statement.setString(2, entity.getLicensePlate());
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

	@Override
	public Taxi getTaxiByVehicleId(long vehicleId) {
		PreparedStatement statement = null;
		Connection connection = null;
		Taxi taxi = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_VEHICLE_ID_SQL);
			statement.setLong(1, vehicleId);
			resultSet = statement.executeQuery();
			taxi = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return taxi;
	}

	@Override
	public List<Taxi> getAllTaxis() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Taxi> taxis = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Taxi taxi = new Taxi();
				taxi.setId(resultSet.getLong("id"));
				taxi.setVehicleId(resultSet.getLong("vehicle_id"));
				taxi.setLicensePlate(resultSet.getString("license_plate"));
				taxis.add(taxi);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return taxis;
	}

	@Override
	protected Taxi resultSetToObject(ResultSet resultSet) {
		Taxi taxi = null;
		try {
			while (resultSet.next()) {
				taxi = new Taxi();
				taxi.setId(resultSet.getLong("id"));
				taxi.setVehicleId(resultSet.getLong("vehicle_id"));
				taxi.setLicensePlate(resultSet.getString("license_plate"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return taxi;
	}

}