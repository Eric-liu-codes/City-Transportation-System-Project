package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IBusDao;
import com.solvd.citytransportationsystemproject.models.Bus;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusDao extends MySQLDao<Bus> implements IBusDao {
    private static final Logger LOGGER = LogManager.getLogger(BusDao.class);

    @Override
    public Bus createEntity(Bus entity){
        String sql = "INSERT INTO Bus (id, bus_number, vehicle_id) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setInt(2, entity.getBusNumber());
            statement.setLong(3, entity.getVehicleId());
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
    public Bus getEntityById(long id){
        String sql = "SELECT * FROM Bus WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Bus bus = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            bus = resultSetToObject(resultSet);
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
        return bus;
    }


    @Override
    public void updateEntity(Bus entity){
        String sql = "UPDATE Bus SET bus_number = ?, vehicle_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entity.getBusNumber());
            statement.setLong(2, entity.getVehicleId());
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
        String sql = "DELETE FROM Bus WHERE id = ?";
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
    public Bus getBusByBusNumber(Integer busNumber){
        String sql = "SELECT * FROM Bus WHERE bus_number = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Bus bus = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, busNumber);
            resultSet = statement.executeQuery();
            bus = resultSetToObject(resultSet);
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
        return bus;
    }

    @Override
    public List<Bus> getAllBuses(){
        String sql = "SELECT * FROM Bus";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Bus> buses = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Bus bus = new Bus();
                bus.setId(resultSet.getLong("id"));
                bus.setBusNumber(resultSet.getInt("bus_number"));
                bus.setVehicleId(resultSet.getLong("vehicle_id"));
                buses.add(bus);
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
        return buses;
    }

    @Override
    protected Bus resultSetToObject(ResultSet resultSet) {
        Bus bus = null;
        try{
            while (resultSet.next()){
                bus = new Bus();
                bus.setId(resultSet.getLong("id"));
                bus.setBusNumber(resultSet.getInt("bus_number"));
                bus.setVehicleId(resultSet.getLong("vehicle_id"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return bus;
    }



}
