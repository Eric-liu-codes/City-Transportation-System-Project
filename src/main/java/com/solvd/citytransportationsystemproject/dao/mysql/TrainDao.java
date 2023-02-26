package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.ITrainDao;
import com.solvd.citytransportationsystemproject.models.Train;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainDao extends MySQLDao<Train> implements ITrainDao {
	private static final Logger LOGGER = LogManager.getLogger(TrainDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Train (id, vehicle_id, train_headcode) VALUES (?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Train WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Train SET vehicle_id = ?, train_headcode = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Train WHERE id = ?";
	private static final String GET_ENTITY_BY_TRAIN_HEADCODE_SQL = "SELECT * FROM Train WHERE train_headcode = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Train";

	@Override
	public Train createEntity(Train entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setLong(2, entity.getVehicleId());
			statement.setString(3, entity.getTrainHeadcode());
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
	public Train getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Train train = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			train = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return train;
	}

	@Override
	public void updateEntity(Train entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setLong(1, entity.getVehicleId());
			statement.setString(2, entity.getTrainHeadcode());
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
	public Train getTrainByTrainHeadcode(String trainHeadcode) {
		PreparedStatement statement = null;
		Connection connection = null;
		Train train = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_TRAIN_HEADCODE_SQL);
			statement.setString(1, trainHeadcode);
			resultSet = statement.executeQuery();
			train = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return train;
	}

	@Override
	public List<Train> getAllTrains() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Train> trains = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Train train = new Train();
				train.setId(resultSet.getLong("id"));
				train.setVehicleId(resultSet.getLong("vehicle_id"));
				train.setTrainHeadcode(resultSet.getString("train_headcode"));
				trains.add(train);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return trains;
	}

	@Override
	protected Train resultSetToObject(ResultSet resultSet) {
		Train train = null;
		try {
			while (resultSet.next()) {
				train = new Train();
				train.setId(resultSet.getLong("id"));
				train.setVehicleId(resultSet.getLong("vehicle_id"));
				train.setTrainHeadcode(resultSet.getString("train_headcode"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return train;
	}

}