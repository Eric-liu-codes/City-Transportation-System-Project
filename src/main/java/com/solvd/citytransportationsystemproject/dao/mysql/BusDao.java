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
    private static final String CREATE_ENTITY_SQL = "INSERT INTO Bus (id, bus_number, vehicle_id) VALUES (?, ?, ?)";
    private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Bus WHERE id = ?";
    private static final String UPDATE_ENTITY_SQL = "UPDATE Bus SET bus_number = ?, vehicle_id = ? WHERE id = ?";
    private static final String DELETE_ENTITY_SQL = "DELETE FROM Bus WHERE id = ?";
    private static final String GET_ENTITY_BY_BUS_NUMBER_SQL = "SELECT * FROM Bus WHERE bus_number = ?";
    private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Bus";



    @Override
    public Bus createEntity(Bus entity){
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_ENTITY_SQL);
            statement.setLong(1, entity.getId());
            statement.setInt(2, entity.getBusNumber());
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
    public Bus getEntityById(long id){
        PreparedStatement statement = null;
        Connection connection = null;
        Bus bus = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            bus = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return bus;
    }


    @Override
    public void updateEntity(Bus entity){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
            statement.setInt(1, entity.getBusNumber());
            statement.setLong(2, entity.getVehicleId());
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
    public void deleteEntity(long id){
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
    public Bus getBusByBusNumber(Integer busNumber){
        PreparedStatement statement = null;
        Connection connection = null;
        Bus bus = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_BUS_NUMBER_SQL);
            statement.setLong(1, busNumber);
            resultSet = statement.executeQuery();
            bus = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
           closeResource(statement);
           closeResource(resultSet);
           ConnectionPool.getInstance().releaseConnection(connection);
        }
        return bus;
    }

    @Override
    public List<Bus> getAllBuses(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Bus> buses = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
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
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
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
