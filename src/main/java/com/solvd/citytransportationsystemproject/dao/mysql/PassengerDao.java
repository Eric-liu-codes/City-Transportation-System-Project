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

    @Override
    public Passenger createEntity(Passenger entity){
        String sql = "INSERT INTO Passenger (id, number_of_rides, person_id) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setInt(2, entity.getNumberOfRides());
            statement.setLong(3, entity.getPersonId());
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
    public Passenger getEntityById(long id){
        String sql = "SELECT * FROM Passenger WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Passenger passenger = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            passenger = resultSetToObject(resultSet);
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
        return passenger;
    }


    @Override
    public void updateEntity(Passenger entity){
        String sql = "UPDATE Passenger SET number_of_rides = ?, person_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entity.getNumberOfRides());
            statement.setLong(2, entity.getPersonId());
            statement.setLong(3, entity.getId());
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
        String sql = "DELETE FROM Passenger WHERE id = ?";
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

    public Passenger getPassengerByPersonId(long personId){
        String sql = "SELECT * FROM Passenger WHERE person_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Passenger passenger = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, personId);
            resultSet = statement.executeQuery();
            passenger = resultSetToObject(resultSet);
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
        return passenger;
    }

    @Override
    public List<Passenger> getAllPassengers(){
        String sql = "SELECT * FROM Passenger";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Passenger> passengers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Passenger passenger = new Passenger();
                passenger.setId(resultSet.getLong("id"));
                passenger.setNumberOfRides(resultSet.getInt("number_of_rides"));
                passenger.setPersonId(resultSet.getLong("person_id"));
                passengers.add(passenger);
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
        return passengers;
    }


    @Override
    protected Passenger resultSetToObject(ResultSet resultSet) {
        Passenger passenger = null;
        try{
            while (resultSet.next()){
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
