package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IVehicleDao;
import com.solvd.citytransportationsystemproject.models.Vehicle;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao extends MySQLDao<Vehicle> implements IVehicleDao {
    private static final Logger LOGGER = LogManager.getLogger(VehicleDao.class);


    @Override
    public Vehicle createEntity(Vehicle entity){
        String sql = "INSERT INTO Vehicle (id, make, model, year, capacity, driver_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getMake());
            statement.setString(3, entity.getModel());
            statement.setInt(4, entity.getYear());
            statement.setInt(5, entity.getCapacity());
            statement.setLong(6, entity.getDriverId());
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
    public Vehicle getEntityById(long id){
        String sql = "SELECT * FROM Vehicle WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Vehicle vehicle = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            vehicle = resultSetToObject(resultSet);
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
        return vehicle;
    }


    @Override
    public void updateEntity(Vehicle entity){
        String sql = "UPDATE Vehicle SET make = ?, model = ?, year = ?, capacity = ?, driver_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getMake());
            statement.setString(2, entity.getModel());
            statement.setInt(3, entity.getYear());
            statement.setInt(4, entity.getCapacity());
            statement.setLong(5, entity.getDriverId());
            statement.setLong(6, entity.getId());
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
        String sql = "DELETE FROM Vehicle WHERE id = ?";
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
    public Vehicle getVehicleByDriverId(long driverId){
        String sql = "SELECT * FROM Vehicle WHERE driver_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Vehicle vehicle = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, driverId);
            resultSet = statement.executeQuery();
            vehicle = resultSetToObject(resultSet);
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
        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehicles(){
        String sql = "SELECT * FROM Vehicle";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Vehicle vehicle = new Vehicle();
                vehicle.setId(resultSet.getLong("id"));
                vehicle.setMake(resultSet.getString("make"));
                vehicle.setModel(resultSet.getString("model"));
                vehicle.setYear(resultSet.getInt("year"));
                vehicle.setCapacity(resultSet.getInt("capacity"));
                vehicle.setDriverId(resultSet.getLong("driver_id"));
                vehicles.add(vehicle);
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
        return vehicles;
    }


    @Override
    protected Vehicle resultSetToObject(ResultSet resultSet) {
        Vehicle vehicle = null;
        try{
            while (resultSet.next()){
                vehicle = new Vehicle();
                vehicle.setId(resultSet.getLong("id"));
                vehicle.setMake(resultSet.getString("make"));
                vehicle.setModel(resultSet.getString("model"));
                vehicle.setYear(resultSet.getInt("year"));
                vehicle.setCapacity(resultSet.getInt("capacity"));
                vehicle.setDriverId(resultSet.getLong("driver_id"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return vehicle;
    }

}
