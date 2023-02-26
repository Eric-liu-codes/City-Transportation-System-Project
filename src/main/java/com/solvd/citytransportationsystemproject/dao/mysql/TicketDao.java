package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.ITicketDao;
import com.solvd.citytransportationsystemproject.models.Ticket;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDao extends MySQLDao<Ticket> implements ITicketDao {
	private static final Logger LOGGER = LogManager.getLogger(TicketDao.class);
	private static final String CREATE_ENTITY_SQL = "INSERT INTO Ticket (id, payment_method, fare, passenger_id) VALUES (?, ?, ?, ?)";
	private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Ticket WHERE id = ?";
	private static final String UPDATE_ENTITY_SQL = "UPDATE Ticket SET payment_method = ?, fare = ?, passenger_id = ? WHERE id = ?";
	private static final String DELETE_ENTITY_SQL = "DELETE FROM Ticket WHERE id = ?";
	private static final String GET_ENTITY_BY_PASSENGER_ID_SQL = "SELECT * FROM Ticket WHERE passenger_id = ?";
	private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Ticket";

	@Override
	public Ticket createEntity(Ticket entity) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(CREATE_ENTITY_SQL);
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getPaymentMethod());
			statement.setFloat(3, entity.getFare());
			statement.setLong(4, entity.getPassengerId());
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
	public Ticket getEntityById(long id) {
		PreparedStatement statement = null;
		Connection connection = null;
		Ticket ticket = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			ticket = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return ticket;
	}

	@Override
	public void updateEntity(Ticket entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
			statement.setString(1, entity.getPaymentMethod());
			statement.setFloat(2, entity.getFare());
			statement.setLong(3, entity.getPassengerId());
			statement.setLong(4, entity.getId());
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
	public Ticket getTicketByPassengerId(long passengerId) {
		PreparedStatement statement = null;
		Connection connection = null;
		Ticket ticket = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ENTITY_BY_PASSENGER_ID_SQL);
			statement.setLong(1, passengerId);
			resultSet = statement.executeQuery();
			ticket = resultSetToObject(resultSet);
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return ticket;
	}

	@Override
	public List<Ticket> getAllTickets() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Ticket> tickets = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ticket ticket = new Ticket();
				ticket.setId(resultSet.getLong("id"));
				ticket.setPaymentMethod(resultSet.getString("payment_method"));
				ticket.setFare(resultSet.getFloat("fare"));
				ticket.setPassengerId(resultSet.getLong("passenger_id"));
				tickets.add(ticket);
			}
		} catch (Exception e) {
			LOGGER.info(e);
		} finally {
			closeResource(statement);
			closeResource(resultSet);
			ConnectionPool.getInstance().releaseConnection(connection);
		}
		return tickets;
	}

	@Override
	protected Ticket resultSetToObject(ResultSet resultSet) {
		Ticket ticket = null;
		try {
			while (resultSet.next()) {
				ticket = new Ticket();
				ticket.setId(resultSet.getLong("id"));
				ticket.setPaymentMethod(resultSet.getString("payment_method"));
				ticket.setFare(resultSet.getFloat("fare"));
				ticket.setPassengerId(resultSet.getLong("passenger_id"));
			}
		} catch (Exception e) {
			LOGGER.info(e);
		}
		return ticket;
	}

}