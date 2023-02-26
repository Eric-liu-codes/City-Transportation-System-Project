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

    @Override
    public Taxi createEntity(Taxi entity){
        String sql = "INSERT INTO Taxi (id, vehicle_id, license_plate) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getVehicleId());
            statement.setString(3, entity.getLicensePlate());
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
    public Taxi getEntityById(long id){
        String sql = "SELECT * FROM Taxi WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Taxi taxi = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            taxi = resultSetToObject(resultSet);
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
        return taxi;
    }


    @Override
    public void updateEntity(Taxi entity){
        String sql = "UPDATE Taxi SET vehicle_id = ?, license_plate = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getVehicleId());
            statement.setString(2, entity.getLicensePlate());
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
        String sql = "DELETE FROM Taxi WHERE id = ?";
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
    public Taxi getTaxiByVehicleId(long vehicleId){
        String sql = "SELECT * FROM Taxi WHERE vehicle_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Taxi taxi = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, vehicleId);
            resultSet = statement.executeQuery();
            taxi = resultSetToObject(resultSet);
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
        return taxi;
    }

    @Override
    public List<Taxi> getAllTaxis(){
        String sql = "SELECT * FROM Taxi";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Taxi> taxis = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Taxi taxi = new Taxi();
                taxi.setId(resultSet.getLong("id"));
                taxi.setVehicleId(resultSet.getLong("vehicle_id"));
                taxi.setLicensePlate(resultSet.getString("license_plate"));
                taxis.add(taxi);
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
        return taxis;
    }


    @Override
    protected Taxi resultSetToObject(ResultSet resultSet) {
        Taxi taxi = null;
        try{
            while (resultSet.next()){
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
