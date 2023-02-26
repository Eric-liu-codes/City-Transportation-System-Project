package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IStationDao;
import com.solvd.citytransportationsystemproject.models.Station;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StationDao extends MySQLDao<Station> implements IStationDao {
	private static final Logger LOGGER = LogManager.getLogger(StationDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Station (id, name, type, address, route_id) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Station WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Station SET name = ?, type = ?, address = ?, route_id = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Station WHERE id = ?";
	private static final String GET_ENTITY_BY_TYPE_SQL = "SELECT * FROM Station WHERE type = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Station";

	@Override
	public Station createEntity(Station entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getName());
			statement.setString(3, entity.getType());
			statement.setString(4, entity.getAddress());
			statement.setLong(5, entity.getRouteId());
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
	public Station getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Station station = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			station = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return station;
	}

	@Override
	public void updateEntity(Station entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setString(1, entity.getName());
			statement.setString(2, entity.getType());
			statement.setString(3, entity.getAddress());
			statement.setLong(4, entity.getRouteId());
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

	public Station getStationByType(String type) {
		PreparedStatement statement = null;
		Connection connection = null;
		Station station = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_TYPE_SQL);
			statement.setString(1, type);
			resultSet = statement.executeQuery();
			station = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return station;
	}

	public List<Station> getAllStations() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Station> stations = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Station station = new Station();
				station.setId(resultSet.getLong("id"));
				station.setName(resultSet.getString("name"));
				station.setType(resultSet.getString("type"));
				station.setAddress(resultSet.getString("address"));
				station.setRouteId(resultSet.getLong("route_id"));
				stations.add(station);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return stations;
	}

	@Override
	protected Station resultSetToObject(ResultSet resultSet) {
		Station station = null;
		try {
			while (resultSet.next()) {
				station = new Station();
				station.setId(resultSet.getLong("id"));
				station.setName(resultSet.getString("name"));
				station.setType(resultSet.getString("type"));
				station.setAddress(resultSet.getString("address"));
				station.setRouteId(resultSet.getLong("route_id"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return station;
	}

}