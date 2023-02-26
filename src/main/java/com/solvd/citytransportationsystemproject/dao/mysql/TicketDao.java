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

    @Override
    public Ticket createEntity(Ticket entity){
        String sql = "INSERT INTO Ticket (id, payment_method, fare, passenger_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getPaymentMethod());
            statement.setFloat(3, entity.getFare());
            statement.setLong(4, entity.getPassengerId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return entity;
    }


    @Override
    public Ticket getEntityById(long id){
        String sql = "SELECT * FROM Ticket WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Ticket ticket = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            ticket = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return ticket;
    }


    @Override
    public void updateEntity(Ticket entity){
        String sql = "UPDATE Ticket SET payment_method = ?, fare = ?, passenger_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getPaymentMethod());
            statement.setFloat(2, entity.getFare());
            statement.setLong(3, entity.getPassengerId());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
    }


    @Override
    public void deleteEntity(long id){
        String sql = "DELETE FROM Ticket WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
    }


    @Override
    public Ticket getTicketByPassengerId(long passengerId){
        String sql = "SELECT * FROM Ticket WHERE passenger_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Ticket ticket = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, passengerId);
            resultSet = statement.executeQuery();
            ticket = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return ticket;
    }


    @Override
    public List<Ticket> getAllTickets(){
        String sql = "SELECT * FROM Ticket";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return tickets;
    }


    @Override
    protected Ticket resultSetToObject(ResultSet resultSet) {
        Ticket ticket = null;
        try{
            while (resultSet.next()){
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
