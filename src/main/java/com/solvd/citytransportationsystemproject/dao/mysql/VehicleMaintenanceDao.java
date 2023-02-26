package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IVehicleMaintenance;
import com.solvd.citytransportationsystemproject.models.VehicleMaintenance;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleMaintenanceDao extends MySQLDao<VehicleMaintenance> implements IVehicleMaintenance {
	private static final Logger LOGGER = LogManager.getLogger(VehicleMaintenance.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO VehicleMaintenance (id, date, type, description, vehicle_id) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM VehicleMaintenance WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE VehicleMaintenance SET date = ?, type = ?, description = ?, vehicle_id = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM VehicleMaintenance WHERE id = ?";
	private static final String GET_ENTITY_BY_TYPE_SQL = "SELECT * FROM VehicleMaintenance WHERE type = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM VehicleMaintenance";

	@Override
	public VehicleMaintenance createEntity(VehicleMaintenance entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setDate(2, entity.getDate());
			statement.setString(3, entity.getType());
			statement.setString(4, entity.getDescription());
			statement.setLong(5, entity.getVehicleId());
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
	public VehicleMaintenance getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		VehicleMaintenance vehicleMaintenance = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			vehicleMaintenance = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return vehicleMaintenance;
	}

	@Override
	public void updateEntity(VehicleMaintenance entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setDate(1, entity.getDate());
			statement.setString(2, entity.getType());
			statement.setString(3, entity.getDescription());
			statement.setLong(4, entity.getVehicleId());
			statement.setLong(5, entity.getId());
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
	public VehicleMaintenance getVehicleMaintenanceByType(String type) {
		PreparedStatement statement = null;
		Connection connection = null;
		VehicleMaintenance vehicleMaintenance = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_TYPE_SQL);
			statement.setString(1, type);
			resultSet = statement.executeQuery();
			vehicleMaintenance = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return vehicleMaintenance;
	}

	@Override
	public List<VehicleMaintenance> getAllVehicleMaintenances() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<VehicleMaintenance> vehicleMaintenanceList = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				VehicleMaintenance vehicleMaintenance = new VehicleMaintenance();
				vehicleMaintenance.setId(resultSet.getLong("id"));
				vehicleMaintenance.setDate(resultSet.getDate("date"));
				vehicleMaintenance.setType(resultSet.getString("type"));
				vehicleMaintenance.setDescription(resultSet.getString("description"));
				vehicleMaintenance.setVehicleId(resultSet.getLong("vehicle_id"));
				vehicleMaintenanceList.add(vehicleMaintenance);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return vehicleMaintenanceList;
	}

	@Override
	protected VehicleMaintenance resultSetToObject(ResultSet resultSet) {
		VehicleMaintenance vehicleMaintenance = null;
		try {
			while (resultSet.next()) {
				vehicleMaintenance = new VehicleMaintenance();
				vehicleMaintenance.setId(resultSet.getLong("id"));
				vehicleMaintenance.setDate(resultSet.getDate("date"));
				vehicleMaintenance.setType(resultSet.getString("type"));
				vehicleMaintenance.setDescription(resultSet.getString("description"));
				vehicleMaintenance.setVehicleId(resultSet.getLong("vehicle_id"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return vehicleMaintenance;
	}

}