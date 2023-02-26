package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IRouteDao;
import com.solvd.citytransportationsystemproject.models.Route;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RouteDao extends MySQLDao<Route> implements IRouteDao {
	private static final Logger LOGGER = LogManager.getLogger(RouteDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Route (id, name, vehicle_id) VALUES (?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Route WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Route SET name = ?, vehicle_id = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Route WHERE id = ?";
	private static final String GET_ENTITY_BY_NAME_SQL = "SELECT * FROM Route WHERE name = ?";
	private static final String GET_ENTITY_BY_VEHICLE_ID_SQL = "SELECT * FROM Route WHERE vehicle_id = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Route";

	@Override
	public Route createEntity(Route entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getName());
			statement.setLong(3, entity.getVehicleId());
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
	public Route getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Route route = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			route = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return route;
	}

	@Override
	public void updateEntity(Route entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setString(1, entity.getName());
			statement.setLong(2, entity.getVehicleId());
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
	public Route getRouteByName(String name) {
		PreparedStatement statement = null;
		Connection connection = null;
		Route route = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_NAME_SQL);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			route = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return route;
	}

	@Override
	public Route getRouteByVehicleId(long vehicleId) {
		PreparedStatement statement = null;
		Connection connection = null;
		Route route = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_VEHICLE_ID_SQL);
			statement.setLong(1, vehicleId);
			resultSet = statement.executeQuery();
			route = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return route;
	}

	@Override
	public List<Route> getAllRoutes() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Route> routes = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Route route = new Route();
				route.setId(resultSet.getLong("id"));
				route.setName(resultSet.getString("name"));
				route.setVehicleId(resultSet.getLong("vehicle_id"));
				routes.add(route);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return routes;
	}

	@Override
	protected Route resultSetToObject(ResultSet resultSet) {
		Route route = null;
		try {
			while (resultSet.next()) {
				route = new Route();
				route.setId(resultSet.getLong("id"));
				route.setName(resultSet.getString("name"));
				route.setVehicleId(resultSet.getLong("vehicle_id"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return route;
	}

}